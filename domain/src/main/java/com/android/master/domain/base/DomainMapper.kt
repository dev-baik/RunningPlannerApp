package com.android.master.domain.base

interface DomainMapper<T : DomainModel?> {

    fun toDomainModel(): T
}