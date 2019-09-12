import de.dikodam.adventofkotlin.reactPolymerWithNextChar
import de.dikodam.adventofkotlin.isSameCharDifferentCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Day05Test {

    @Test
    fun `eliminates char at end of string if equal with parameter but different case`() {
        assertThat(reactPolymerWithNextChar("aC", 'c'))
            .isEqualTo("a")
    }

    @ParameterizedTest
    @MethodSource("charComparisonProvider")
    fun `same chars with different case`(data: CharComparison) {
        assertThat(data.firstChar.isSameCharDifferentCase(data.secondChar)).isEqualTo(data.expectedBoolean)
    }

    fun charComparisonProvider() = Stream.of(
        CharComparison('A', 'a', true),
        CharComparison('b', 'B', true),
        CharComparison('c', 'c', false),
        CharComparison('C', 'C', false),
        CharComparison('d', 'e', false),
        CharComparison('f', 'G', false)
    )

    data class CharComparison(
        val firstChar: Char,
        val secondChar: Char,
        val expectedBoolean: Boolean
    )
}
