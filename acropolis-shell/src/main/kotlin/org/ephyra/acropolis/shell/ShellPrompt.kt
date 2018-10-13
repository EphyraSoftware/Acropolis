package org.ephyra.acropolis.shell

import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

/**
 * Custom prompt for the Acropolis shell.
 */
@Component
class ShellPrompt : PromptProvider {
    override fun getPrompt(): AttributedString {
        return AttributedString("$ ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN))
    }
}
