import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Tests {
    @Test
    fun check_length_false(){
        val password = "123"
        val validator = Validator()
        validator.addRule(PasswordLengthRule(6, 10))
        assertEquals(false, validator.validate(password))
    }

    @Test
    fun check_length_true(){
        val password = "123456"
        val validator = Validator()
        validator.addRule(PasswordLengthRule(3, 10))
        assertEquals(true, validator.validate(password))
    }

    @Test
    fun check_case_false() {
        val password = "123456lower"
        val validator = Validator()
        validator.addRule(CharacterCaseRule())
        assertEquals(false, validator.validate(password))
    }

    @Test
    fun check_case_true() {
        val password = "123456lowerUPPER"
        val validator = Validator()
        validator.addRule(CharacterCaseRule())
        assertEquals(true, validator.validate(password))
    }

    @Test
    fun check_digits_false() {
        val password = "lowerUPPER"
        val validator = Validator()
        validator.addRule(DigitsRule())
        assertEquals(false, validator.validate(password))
    }

    @Test
    fun check_digits_true() {
        val password = "123lowerUPPER"
        val validator = Validator()
        validator.addRule(DigitsRule())
        assertEquals(true, validator.validate(password))
    }

    @Test
    fun check_special_characters_false() {
        val password = "lowerUPPER"
        val validator = Validator()
        validator.addRule(SpecialCharactersRule())
        assertEquals(false, validator.validate(password))
    }

    @Test
    fun check_special_characters_true() {
        val password = "lowerUPPER@"
        val validator = Validator()
        validator.addRule(SpecialCharactersRule())
        assertEquals(true, validator.validate(password))
    }

    @Test
    fun check_dict_false() {
        val password = "pepper"
        val validator = Validator()
        validator.addRule(NoFrequentWordsRule())
        assertEquals(false, validator.validate(password))
    }

    @Test
    fun check_dict_true() {
        val password = "lowerUPPER"
        val validator = Validator()
        validator.addRule(NoFrequentWordsRule())
        assertEquals(true, validator.validate(password))
    }

    @Test
    fun check_entropy_false() {
        val password = "1231111lowerUPPER"
        val validator = Validator()
        validator.addRule(HighEntropyRule(10.0))
        assertEquals(false, validator.validate(password))
    }

    @Test
    fun check_entropy_true() {
        val password = "lowerUPPER"
        val validator = Validator()
        validator.addRule(HighEntropyRule(0.5))
        assertEquals(true, validator.validate(password))
    }
}