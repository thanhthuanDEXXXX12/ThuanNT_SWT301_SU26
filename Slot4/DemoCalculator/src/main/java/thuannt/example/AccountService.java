package thuannt.example;

public class AccountService {

    // Hàm kiểm tra định dạng email
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra xem có chứa dấu '@' và dấu '.' hay không
        return email.contains("@") && email.contains(".");
    }

    // Hàm đăng ký tài khoản cần kiểm thử chính
    public boolean registerAccount(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống");
        }
        if (password == null || password.length() <= 6) {
            throw new IllegalArgumentException("Password phải lớn hơn 6 ký tự");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email không đúng định dạng");
        }
        return true; // Đăng ký thành công
    }
}