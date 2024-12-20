package br.com.alura.challenge.api_financas.model;

public enum Categoria {
    ALIMENTACAO,
    SAUDE,
    MORADIA,
    TRANSPORTE,
    EDUCACAO,
    LAZER,
    IMPREVISTOS,
    OUTRAS;

    public String getNomeComAcento() {
        return switch (this) {
            case ALIMENTACAO -> "Alimentação";
            case SAUDE -> "Saúde";
            case MORADIA -> "Moradia";
            case TRANSPORTE -> "Transporte";
            case EDUCACAO -> "Educação";
            case LAZER -> "Lazer";
            case IMPREVISTOS -> "Imprevistos";
            case OUTRAS -> "Outras";
        };
    }
}
