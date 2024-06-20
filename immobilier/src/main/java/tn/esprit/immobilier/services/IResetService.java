package tn.esprit.immobilier.services;

public interface IResetService {
    public void sendResetPasswordEmail(String email);
    public void resetPassword(String token, String newPassword);
}
