package thuannt.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        // Áp dụng Vòng đời Lifecycle để reset trạng thái trước mỗi bài test
        accountService = new AccountService();
    }

    @ParameterizedTest
    @ValueSource(strings = {"thuan@fpt.edu.vn", "abc.xyz@gmail.com", "test.user@yahoo.com"})
    @DisplayName("Test các email đúng định dạng bằng ValueSource")
    void isValidEmail_ValidFormats_ReturnsTrue(String email) {
        // Act
        boolean result = accountService.isValidEmail(email);
        // Assert
        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
            "''",         // Email rỗng
            "'   '",      // Email chỉ có khoảng trắng
            "thuanfpt",   // Thiếu cả @ và .
            "thuan@fpt",  // Thiếu dấu .
            "thuan.edu"   // Thiếu dấu @
    })
    @DisplayName("Test các email sai định dạng bằng CsvSource")
    void isValidEmail_InvalidFormats_ReturnsFalse(String email) {
        // Act
        boolean result = accountService.isValidEmail(email);
        // Assert
        falseOrNullTest(result);
    }

    private void falseOrNullTest(boolean result) {
        assertFalse(result);
    }

    @Test
    @DisplayName("Test email truyền vào bị null")
    void isValidEmail_NullEmail_ReturnsFalse() {
        assertFalse(accountService.isValidEmail(null));
    }

    @ParameterizedTest(name = "Mẫu {index} => Người dùng: {0}, Pass: {1}, Email: {2}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("Kiểm thử tính năng đăng ký đọc từ file CSV")
    void registerAccount_FromCSV(String username, String password, String email, boolean expectedResult) {
        if (expectedResult) {
            // Case đúng: Phải trả về true
            boolean actual = accountService.registerAccount(username, password, email);
            assertTrue(actual);
        } else {
            // Case sai quy tắc: Phải ném ra lỗi IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> {
                accountService.registerAccount(username, password, email);
            });
        }
    }
}