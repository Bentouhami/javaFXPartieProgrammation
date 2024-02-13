package be.bentouhami.reservotelapp.Model.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    /* Expression régulière pour valider le format d'une adresse email.
        Ce pattern vérifie donc que l'email contient des caractères permis
        avant et après le signe @, mais ne vérifie pas la validité du domaine. */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /*Expression régulière pour valider le format d'un numéro de téléphone.
        Cette expression vérifie les formats de numéros de téléphone
        qui ont un code de pays optionnel suivi de 10 chiffres. */
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /*Les critères pour un mot de passe fort pourraient inclure une combinaison de lettres, de chiffres et de symboles.
        Ce modèle est destiné à valider un mot de passe "fort", incluant une combinaison de chiffres,
        lettres en majuscules et minuscules, caractères spéciaux, sans espaces,
         et d'une longueur minimale de 8 caractères.*/
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    //private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    /**
     * Valide une adresse email selon l'expression régulière définie.
     *
     * @param email L'adresse email à valider.
     * @return true si l'email est valide, false sinon.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * Valide un numéro de téléphone selon l'expression régulière définie.
     *
     * @param phone Le numéro de téléphone à valider.
     * @return true si le numéro est valide, false sinon.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }

    /**
     * Valide un mot de passe selon l'expression régulière définie.
     *
     * @param password Le mot de passe à valider.
     * @return true si le mot de passe est fort, false sinon.
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

    /**
     * Vérifie si un champ de texte n'est pas vide ou ne contient pas seulement des espaces blancs.
     *
     * @param text Le contenu du champ de texte à valider.
     * @return true si le champ n'est pas vide et ne contient pas seulement des espaces blancs, false sinon.
     */
    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Vérifie si un champ de texte n'est pas vide ou ne contient pas seulement des espaces blancs.
     *
     * @param inputs Le contenu du champ de texte à valider.
     * @return true si le champ n'est pas vide et ne contient pas seulement des espaces blancs, false sinon.
     */
    public static boolean isNotEmpty(String... inputs) {
        for (String text : inputs) {
            return isNotEmpty(text);
        }
        return false;
    }

    public static int getEmptyElementIndex(String[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            if (isNotEmpty(inputs[i])) {
                return i; // Retourne l'indice de l'élément vide ou contenant uniquement des espaces
            }
        }
        return -1; // Tous les éléments sont non-vides
    }

}// ens Validator classe

