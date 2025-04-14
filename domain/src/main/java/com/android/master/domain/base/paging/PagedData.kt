package com.android.master.domain.base.paging

import com.android.master.domain.base.DomainModel

data class PagedData<VideoSearch>(
    val data: VideoSearch,
    val paging: Paging?
) : DomainModel

data class Paging(
    val loadedSize: Int
) : DomainModel