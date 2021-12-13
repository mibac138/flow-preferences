package com.fredporciuncula.flow.preferences

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

interface Preference<T> {

  val key: String

  val defaultValue: T

  fun get(): T

  fun set(value: T)

  suspend fun setAndCommit(value: T): Boolean

  fun isSet(): Boolean

  fun isNotSet(): Boolean

  fun delete()

  suspend fun deleteAndCommit(): Boolean

  fun asFlow(): Flow<T>

  fun asCollector(): FlowCollector<T>

  fun asSyncCollector(throwOnFailure: Boolean = false): FlowCollector<T>
}

/**
 * Use when providing a default value is not desired and using `asFlow().stateIn(scope)` is
 * not possible (`stateIn` requires to be run in a `suspend` context)
 */
fun <T> Preference<T>.asStateFlow(scope: CoroutineScope, started: SharingStarted): StateFlow<T> =
  this.asFlow().stateIn(scope, started, this.defaultValue)