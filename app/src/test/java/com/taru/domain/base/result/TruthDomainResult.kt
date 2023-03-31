package com.taru.domain.base.result

import com.google.common.truth.Fact
import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Truth.assertAbout

/**
 * Created by Niraj on 17-03-2023.
 */

class TruthDomainResultSubject<T>(metadata: FailureMetadata, var actual: DomainResult<T>) : Subject(metadata, actual) {
// <TruthDomainResultSubject, DomainResult<T>>
    fun isEqual(value:T) {
        if (actual is DomainResult.Failure) {
            failWithoutActual(Fact.simpleFact("expected to be a Sucess!"))
        }
        val target = actual as DomainResult.Success<*>
        if (target.value != value) {
            failWithActual(Fact.simpleFact("expected to be have '$value' but have '${target.value}' instead"))
        }
    }
    fun isError(){
        if (actual !is DomainResult.Failure) {
            failWithoutActual(Fact.simpleFact("expected to be a failure!"))
        }
    }
}

fun <T> success(): Subject.Factory<TruthDomainResultSubject<T>, DomainResult<T>> {
    return Subject.Factory { failureMetadata, target -> TruthDomainResultSubject(failureMetadata, target) }
}

fun <T> assertThat(domainResult: DomainResult<T>): TruthDomainResultSubject<T> {
    return assertAbout(success<T>()).that(domainResult)
}
