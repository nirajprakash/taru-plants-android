package com.taru.domain.identify.usecase

import android.net.Uri
import com.taru.domain.base.result.DomainResult
import com.taru.domain.identify.repository.IdentifyRepository
import javax.inject.Inject

/**
 * Created by Niraj on 27-01-2023.
 */
internal class IdentifyUseCase @Inject constructor(private var identifyRepository: IdentifyRepository)  {
    suspend operator fun invoke(organ: String, uri: Uri) = identifyRepository.identify(organ, uri)
}