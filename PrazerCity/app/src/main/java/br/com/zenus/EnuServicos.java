package br.com.zenus;

/**
 * Created by Tuca on 14/06/2017.
 */

public enum EnuServicos {


    LOCAIS(1, "http://192.168.25.9/prazer-city/buscar_locais.php"),
    AVALIAR(2, "http://192.168.25.9/prazer-city/avaliar.php");

    /**
     * Opção que será armazenada.
     */
    private final Integer codigo;

    /**
     * Nome amigavel pro usuário.
     */
    private final String nomeAmigavel;

    EnuServicos(final Integer codigo, final String nomeAmigavel) {
        this.codigo = codigo;
        this.nomeAmigavel = nomeAmigavel;
    }

    public Integer getCodigo() {

        return codigo;
    }

    public String getNomeAmigavel() {
        return nomeAmigavel;
    }

    public static EnuServicos valueOf(final Integer codigo) {

        EnuServicos result = null;

        final EnuServicos[] values = EnuServicos.values();

        for (final EnuServicos enumAtributoEspecial : values) {

            if (enumAtributoEspecial.codigo.equals(codigo)) {
                result = enumAtributoEspecial;
                break;
            }

        }

        return result;
    }
}
