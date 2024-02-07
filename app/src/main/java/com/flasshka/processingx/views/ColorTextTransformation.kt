package com.flasshka.processingx.views

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import java.lang.StringBuilder

class ColorTextTransformation : VisualTransformation {
    private val keywords = listOf(
        "import",
        "fun",
        "var",
        "val",
        "return",
        "else",
        "if",
        "when",
        "by",
        "class",
        "override"
    )

    private val spaces = listOf(' ', '\n', '\t')

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.text),
            OffsetMapping.Identity)
    }

    private fun buildAnnotatedStringWithColors(text: String): AnnotatedString {
        return buildAnnotatedString {
            val sb = StringBuilder()

            var hasOneBracket = false
            var hasTwoBracket = false

            for (ind in text.indices) {
                val cur = text[ind]
                if (cur in spaces && !(hasTwoBracket || hasOneBracket) || ind == text.length - 1) {
                    val wordWithoutCur = sb.toString()
                    sb.append(cur)
                    val word = sb.toString()
                    sb.clear()

                    val color = if(wordWithoutCur in keywords) {
                        Color(215, 107, 0)
                    } else if(wordWithoutCur.toIntOrNull() != null) {
                        Color(70, 200, 160  )
                    } else {
                        Color(200, 200, 200)
                    }

                    withStyle(SpanStyle(color)) {
                        append(word)
                    }
                } else if(cur in listOf('\'', '\"') && !hasTwoBracket && !hasOneBracket) {

                    val word = sb.toString()
                    sb.clear()
                    sb.append(cur)

                    val color = if(word in keywords) {
                        Color(215, 107, 0)
                    } else {
                        Color(200, 200, 200)
                    }

                    withStyle(SpanStyle(color)) {
                        append(word)
                    }


                    when(cur) {
                        '\'' -> hasOneBracket = true
                        '\"' -> hasTwoBracket = true
                    }
                } else if(cur == '\'' && hasOneBracket || cur == '\"' && hasTwoBracket) {
                    hasOneBracket = false
                    hasTwoBracket = false

                    sb.append(cur)
                    val word = sb.toString()
                    sb.clear()

                    val color = Color(43, 154, 60)

                    withStyle(SpanStyle(color)) {
                        append(word)
                    }
                } else {
                    sb.append(cur)
                }
            }
        }
    }
}