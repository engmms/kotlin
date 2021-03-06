/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.codeInsight.generate

import com.intellij.codeInsight.CodeInsightSettings
import com.intellij.openapi.util.io.FileUtil
import org.jetbrains.kotlin.idea.actions.generate.KotlinGenerateEqualsAndHashcodeAction
import org.jetbrains.kotlin.test.InTextDirectivesUtils
import java.io.File

abstract class AbstractGenerateHashCodeAndEqualsActionTest : AbstractCodeInsightActionTest() {
    override fun createAction(fileText: String) = KotlinGenerateEqualsAndHashcodeAction()

    override fun doTest(path: String) {
        val fileText = FileUtil.loadFile(File(path), true)

        val codeInsightSettings = CodeInsightSettings.getInstance()
        val useInstanceOfOnEqualsParameterOld = codeInsightSettings.USE_INSTANCEOF_ON_EQUALS_PARAMETER

        try {
            codeInsightSettings.USE_INSTANCEOF_ON_EQUALS_PARAMETER =
                    InTextDirectivesUtils.isDirectiveDefined(fileText, "// USE_IS_CHECK")
            super.doTest(path)
        } finally {
            codeInsightSettings.USE_INSTANCEOF_ON_EQUALS_PARAMETER = useInstanceOfOnEqualsParameterOld
        }
    }
}
