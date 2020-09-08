import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Pessoa {
    private String nome;
    private String sexo;
    private String email;
    private LocalDate dataNascimento;

    public Pessoa(String nome, String sexo, String email, LocalDate dataNascimento) {
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;

        try {
            if (dataNascimento.compareTo(LocalDate.now()) <= 0) {
                this.dataNascimento = dataNascimento;
            } else {
                throw new Exception("A data de nascimento não pode ser no futuro!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void idade() {
        System.out.printf("%s tem %s ano(s)%n", nome, String.valueOf(Period.between(dataNascimento, LocalDate.now()).getYears()));
    }

    public void descricao() {
        System.out.printf("%s é do sexo %s, seu email é %s e nasceu em %s%n", nome, sexo, email, dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void aniversario() {
        if (dataNascimento.withYear(LocalDate.now().getYear()).compareTo(LocalDate.now()) == 0) {
            System.out.printf("Hoje é o aniversário de %s!!!%n", nome);
        } else {
            System.out.printf("O aniversário de %s é em %s%n", nome, dataNascimento.format(DateTimeFormatter.ofPattern("dd/MMM")).toUpperCase());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
