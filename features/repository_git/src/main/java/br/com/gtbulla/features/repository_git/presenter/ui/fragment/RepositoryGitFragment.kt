package br.com.gtbulla.features.repository_git.presenter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import br.com.gtbulla.features.repository_git.databinding.RepositoryFragmentBinding
import br.com.gtbulla.features.repository_git.presenter.ui.adapter.RepositoryAdapter
import br.com.gtbulla.features.repository_git.presenter.ui.adapter.RepositoryLoadStateAdapter
import br.com.gtbulla.features.repository_git.presenter.viewmodel.RepositoryViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class RepositoryGitFragment : Fragment() {
    private var _binding: RepositoryFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepositoryViewModel by viewModel()
    private var adapter: RepositoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = RepositoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        fillView()
    }

    private fun setupView() {
        adapter = RepositoryAdapter()
        with(binding) {
            listRepositories.adapter = adapter?.withLoadStateHeaderAndFooter(
                header = RepositoryLoadStateAdapter { adapter?.retry() },
                footer = RepositoryLoadStateAdapter { adapter?.retry() }
            )
            adapter?.addLoadStateListener { loadState -> renderUi(loadState) }
            buttonRetry.setOnClickListener { adapter?.retry() }
        }
    }

    private fun fillView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getList().collectLatest { list ->
                adapter?.submitData(list)
            }
        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty = adapter?.itemCount == 0
        with(binding) {
            listRepositories.isVisible = !isListEmpty
            loadRepositories.isVisible = loadState.source.refresh is LoadState.Loading
            textNoneRepository.isVisible = loadState.source.append is LoadState.Loading
            buttonRetry.isVisible = loadState.source.append is LoadState.Error
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }

}
