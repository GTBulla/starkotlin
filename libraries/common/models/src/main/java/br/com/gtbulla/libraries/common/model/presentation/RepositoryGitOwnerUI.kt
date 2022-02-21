package br.com.gtbulla.libraries.common.model.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryGitOwnerUI(
    val login: String,
    val avatarUrl: String,
) : Parcelable
