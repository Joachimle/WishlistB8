package beight.wishlist.service;

public enum ServiceMessage {

    // Login
    LOGIN_SUCCESSFUL(""),
    USER_NOT_FOUND("Der var ingen bruger med det brugernavn."),
    PASSWORD_INCORRECT("Adgangskoden var forkert."),

    // Create
    USER_CREATED(""),

    // Update
    USERNAME_UPDATED(""),
    PASSWORD_UPDATED("Adgangskoden blev ændret."),
    PASSWORDS_NOT_MATCHING("Den nye adgangskode var ikke ens i de to felter."),

    // Create and update
    USERNAME_ALREADY_TAKEN("Brugernavnet var ikke ledigt."),
    USERNAME_WRONG_FORMAT("Et brugernavn skal indeholde 2-20 engelske bogstaver eller tal."),
    PASSWORD_WRONG_FORMAT("En adgangskode skal indeholde 2-20 engelske bogstaver eller tal."),
    USERNAME_SIMILAR("Det nye brugernavn var magen til det gamle."),
    PASSWORD_SIMILAR("Den nye adgangskode var magen til den gamle."),

    // Delete
    USER_DELETED("Brugeren blev slettet."),

    // Error
    UNEXPECTED_INPUT("Der opstod en uventet fejl på webserveren."), // Fejl fra controller
    UNEXPECTED_OUTPUT("Der opstod en uventet fejl."); // Fejl fra repository

    public final String dansk;

    ServiceMessage(String dansk) {
        this.dansk = dansk;
    }
}