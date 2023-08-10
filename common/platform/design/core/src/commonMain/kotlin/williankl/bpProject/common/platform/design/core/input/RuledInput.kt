package williankl.bpProject.common.platform.design.core.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import williankl.bpProject.common.platform.design.core.colors.BeautifulColor
import williankl.bpProject.common.platform.design.core.colors.BeautifulPainter
import williankl.bpProject.common.platform.design.core.text.TextSize

public sealed class InputRules {
    public sealed class RegexRule(
        internal val regexRule: Regex
    ) : InputRules() {
        public data class Length(private val length: Int) : RegexRule(
            regexRule = Regex(".{$length,}")
        )

        public object HasNumber : RegexRule(
            regexRule = Regex("(.*?)[0-9](.*?)")
        )

        public object HasUppercaseLetter : RegexRule(
            regexRule = Regex("(.*?)[A-Z](.*?)")
        )

        public object Email : RegexRule(
            regexRule = Regex("(.*?)@(.*?)\\.(.*?)")
        )
    }

    public sealed class BooleanRule(
        internal val boolean: (String) -> Boolean
    ) : InputRules() {
        public class Matches(
            vararg fields: String,
        ) : BooleanRule(
            boolean = { input ->
                (fields.toList() + input).distinct().size == 1
            }
        )
    }
}

public class RuledInputState(
    private val rules: List<InputRules>
) {
    public var isValid: Boolean by mutableStateOf(false)
        internal set

    public var validRules: List<InputRules> by mutableStateOf(emptyList())
        internal set

    public var invalidRules: List<InputRules> by mutableStateOf(emptyList())
        internal set

    internal fun handleInputChange(input: String) {
        val validRules = mutableListOf<InputRules>()
        val invalidRules = mutableListOf<InputRules>()
        rules.forEach { rule ->
            when (rule) {
                is InputRules.BooleanRule -> {
                    if (rule.boolean(input)) validRules.add(rule)
                    else invalidRules.add(rule)
                }

                is InputRules.RegexRule -> {
                    if (input.matches(rule.regexRule)) validRules.add(rule)
                    else invalidRules.add(rule)
                }
            }
        }

        this.validRules = validRules
        this.invalidRules = invalidRules
        isValid = this.validRules.containsAll(rules)
    }

    public inline fun <reified T : InputRules> checkIfHasValidRule(): Boolean {
        return validRules
            .filterIsInstance<T>()
            .isNotEmpty()
    }
}

@Composable
public fun rememberRuledInputState(vararg rules: InputRules): RuledInputState {
    return rememberRuledInputState(rules.toList())
}

@Composable
public fun rememberRuledInputState(rules: List<InputRules>): RuledInputState {
    return remember { RuledInputState(rules) }
}

@Composable
public fun RuledInput(
    state: RuledInputState,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    size: TextSize = TextSize.Regular,
    fontStyle: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    color: BeautifulPainter = BeautifulColor.NeutralHigh,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    sideContentsOnExtremes: Boolean = false,
    startContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
) {
    LaunchedEffect(text) {
        state.handleInputChange(text)
    }

    Input(
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        hint = hint,
        size = size,
        fontStyle = fontStyle,
        weight = weight,
        enabled = enabled,
        readOnly = readOnly,
        color = color,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        sideContentsOnExtremes = sideContentsOnExtremes,
        startContent = startContent,
        endContent = endContent,
    )
}

@Composable
public fun RuledLabeledInput(
    state: RuledInputState,
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    size: TextSize = TextSize.Regular,
    fontStyle: FontStyle = FontStyle.Normal,
    weight: FontWeight = FontWeight.Normal,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    color: BeautifulPainter = BeautifulColor.NeutralHigh,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    sideContentsOnExtremes: Boolean = false,
    startContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
) {
    LaunchedEffect(text) {
        state.handleInputChange(text)
    }

    LabeledInput(
        label = label,
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        hint = hint,
        size = size,
        fontStyle = fontStyle,
        weight = weight,
        enabled = enabled,
        readOnly = readOnly,
        color = color,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        sideContentsOnExtremes = sideContentsOnExtremes,
        startContent = startContent,
        endContent = endContent,
    )
}
