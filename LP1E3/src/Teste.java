import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Teste {
    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Lugar[] lugar = new Lugar[5];
        lugar[0] = new Lugar("Rio de Janeiro", "Cidade");
        lugar[1] = new Lugar("Itália", "País");
        lugar[2] = new Lugar("São Paulo", "Cidade");
        lugar[3] = new Lugar("Brasil", "País");
        lugar[4] = new Lugar("Moscou", "Cidade");
        for (int i = 0; i < lugar.length; i++) {
            if (lugar[i] != null) {
                lugar[i].descricao();
                lugar[i].darNota(i);
                lugar[i].viajar();
                lugar[i].darNota(i);
                lugar[i].descricao();
                System.out.println();
            }
        }

        Pessoa[] pessoa = new Pessoa[5];
        pessoa[0] = new Pessoa("Rodrigo", "Masculino", "rodrigo.prado1705@gmail.com", LocalDate.parse("17/05/2001", formatter));
        pessoa[1] = new Pessoa("Pedro", "Masculino", "pedro.22@outlook.com", LocalDate.parse("09/09/2000", formatter));
        pessoa[2] = new Pessoa("Mariana", "Feminino", "mariana.farias@gmail.com", LocalDate.parse("09/05/2000", formatter));
        pessoa[3] = new Pessoa("Ana Paula", "Feminino", "ana.paula22@hotmail.com", LocalDate.parse("10/09/2010", formatter));
        pessoa[4] = new Pessoa("Rita", "Feminino", "rita.cassia0307@yahoo.com", LocalDate.parse("03/07/1956", formatter));
        for (int i = 0; i < pessoa.length; i++) {
            if (pessoa[i] != null) {
                pessoa[i].descricao();
                pessoa[i].idade();
                pessoa[i].aniversario();
                System.out.println();
            }
        }

        Aluno[] aluno = new Aluno[5];
        aluno[0] = new Aluno("Rodrigo", "30000397");
        aluno[1] = new Aluno("Pedro", "30000398");
        aluno[2] = new Aluno("Mariana", "30000399");
        aluno[3] = new Aluno("Ana Paula", "30000400");
        aluno[4] = new Aluno("Rita", "30000401");
        for (int i = 0; i < pessoa.length; i++) {
            if (aluno[i] != null) {
                double[] notas = {i + 2, i + 3, i + 4, i + 5};
                aluno[i].setNotas(notas);
                aluno[i].descricao();
                aluno[i].media();
                aluno[i].darNota(i + 4, 0);
                aluno[i].descricao();
                aluno[i].media();
                System.out.println();
            }
        }

        Instrumento[] instrumento = new Instrumento[5];
        instrumento[0] = new Instrumento("Violão", 6);
        instrumento[1] = new Instrumento("Flauta", 0);
        instrumento[2] = new Instrumento("Guitarra", 6);
        instrumento[3] = new Instrumento("Violino", 4);
        instrumento[4] = new Instrumento("Piano", 230);
        for (int i = 0; i < instrumento.length; i++) {
            if (instrumento[i] != null) {
                instrumento[i].descricao();
                instrumento[i].tocarOuParar();
                instrumento[i].descricao();
                instrumento[i].afinar();
                instrumento[i].descricao();
                System.out.println();
            }
        }

        Profissional[] profissional = new Profissional[5];
        profissional[0] = new Profissional("Rodrigo", "Programador", 3000.00);
        profissional[1] = new Profissional("Pedro", "Designer", 1500.00);
        profissional[2] = new Profissional("Mariana", "Médica", 12500.00);
        profissional[3] = new Profissional("Ana Paula", "Professora", 4000.00);
        profissional[4] = new Profissional("Rita", "Vendedora", 2000.00);
        for (int i = 0; i < profissional.length; i++) {
            if (profissional[i] != null) {
                profissional[i].descricao();
                profissional[i].trabalhar();
                profissional[i].descricao();
                profissional[i].tirarFerias();
                profissional[i].descricao();
                System.out.println();
            }
        }

        Calcado[] calcado = new Calcado[5];
        calcado[0] = new Calcado("Sapato social", 59.90);
        calcado[1] = new Calcado("Bota", 99.90);
        calcado[2] = new Calcado("Sapato social italiano", 179.99);
        calcado[3] = new Calcado("Bota de couro", 119.99);
        calcado[4] = new Calcado("Sapatilha", 299.99);
        for (int i = 0; i < calcado.length; i++) {
            if (calcado[i] != null) {
                calcado[i].descricao();
                calcado[i].calcar();
                calcado[i].descalcar();
                calcado[i].descricao();
                System.out.println();
            }
        }
    }
}
