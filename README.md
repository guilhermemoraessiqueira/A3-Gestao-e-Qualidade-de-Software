# Refatoração de Código: Banco de Doações
## Projeto A3 - Gestão e Qualidade de Software

**Autores:**
- Guilherme Moraes Siqueira - RA 8222240392
- Heloisa Soares Ferreira - RA 824152581
- João Vitor Cordeiro Lopes - RA 82429891
- Kauan Reis dos Santos- RA 824124128
- Thiago Amaral da Silva Barros – RA 822151695

---

## 🎯 Visão Geral

Este repositório contém a refatoração completa do projeto **Banco de Doações**, um sistema de gerenciamento de doações de sangue. O projeto original apresentava uma arquitetura monolítica com sérias deficiências de design. A refatoração aplicou princípios de **Clean Code**, **SOLID** e o padrão **MVC** para transformar o código em uma arquitetura profissional, testável e fácil de manter.

### Objetivos da Refatoração

- ✅ Separar responsabilidades em camadas bem definidas
- ✅ Implementar interfaces para abstração e flexibilidade
- ✅ Centralizar lógica de negócio em classes Service
- ✅ Melhorar tratamento de erros e exceções
- ✅ Aumentar testabilidade do código
- ✅ Aplicar princípios SOLID (SRP, OCP, LSP, ISP, DIP)
- ✅ Implementar testes unitários com TDD

---

## 🔴 Deficiências do Código Legado

O código original (ramo `main`) apresentava os seguintes problemas:

### 1. **Mistura de Responsabilidades**
```
Problema: Classes de interface gráfica manipulavam lógica de negócio e acesso a dados
Impacto:  Difícil de testar, difícil de manter, violação do SRP
```

**Exemplo do Legado:**
```java
// TelaCadastro.java - Tudo misturado!
private void cadastrarDoador() {
    // Captura de entrada
    String nome = jTextField1.getText();
    
    // Validação (lógica de negócio)
    if (idade < 16) {
        JOptionPane.showMessageDialog(null, "Menor de idade");
        return;
    }
    
    // Acesso a dados
    doadorDAO.adicionarDoador(doador);
    
    // Apresentação
    JOptionPane.showMessageDialog(this, "Sucesso!");
}
```

### 2. **Falta de Abstração**
```
Problema: DAO sem interface, alto acoplamento
Impacto:  Difícil de estender, difícil de testar, difícil de trocar implementação
```

**Exemplo do Legado:**
```java
// DoadorDAO sem interface
public class DoadorDAO {
    public void adicionarDoador(Doador doador) { ... }
}

// Uso direto da implementação
DoadorDAO dao = new DoadorDAO();  // Acoplado à implementação
```

### 3. **Tratamento de Erros Inadequado**
```
Problema: Blocos catch vazios, erros silenciosos
Impacto:  Difícil depuração, perda de informações sobre erros
```

**Exemplo do Legado:**
```java
try {
    statement = connection.prepareStatement(sql);
    // ... operações ...
} catch (SQLException ex) {
    // Bloco vazio - erro perdido!
}
```

### 4. **Baixa Testabilidade**
```
Problema: Impossível testar lógica isoladamente
Impacto:  Sem testes automatizados, risco alto de regressão
```

---

## 🟢 Solução Implementada

A refatoração reorganizou o código em uma **arquitetura em camadas** com separação clara de responsabilidades.

### Estrutura de Pacotes

```
src/
├── model/                 # Entidades de dados
│   ├── Doador.java
│   └── Doacao.java
├── repository/            # Interfaces de acesso a dados
│   ├── DoadorRepository.java
│   └── DoacaoRepository.java
├── daoImpl/               # Implementações de acesso a dados
│   ├── DoadorDAO.java
│   └── DoacaoDAO.java
├── service/              # Lógica de negócio
│   ├── DoadorService.java
│   └── DoacaoService.java
├── controller/           # Coordenação
│   ├── DoadorController.java
│   └── DoacaoController.java
├── telas/                # Interface gráfica
│   ├── TelaCadastro.java
│   ├── TelaChecarDoacoes.java
│   ├── TelaInicial.java
│   └── TelaRegistro.java
└── config/               # Configuração
    └── DataBaseManager.java

test/
└── config/java/          # Testes unitários
    ├── DoadorServiceTest.java
    ├── DoadorTest.java
    └── DataBaseManagerTest.java
```

### Camadas da Arquitetura

#### 1. **Model (Entidades)**
Responsável por armazenar dados consistentes.

```java
public class Doador {
    private String cpfDoador;
    private int idade;
    private String sexo;
    private double peso;
    private String nome;

    public Doador(String cpfDoador, int idade, String sexo, double peso, String nome) {
        this.cpfDoador = cpfDoador;
        this.idade = idade;
        this.sexo = sexo;
        this.peso = peso;
        this.nome = nome;
    }
    // Getters e Setters
}
```

**Melhorias:**
- ✅ Tipos corretos nos parâmetros (int, double)
- ✅ Sem conversões manuais de tipo
- ✅ Documentação clara

#### 2. **Repository (Interface de Acesso a Dados)**
Define o contrato para acesso a dados.

```java
public interface DoadorRepository {
    void adicionar(Doador doador);
    List<Doador> listarTodos();
    Doador buscarPorCpf(String cpf);
    void atualizar(Doador doador);
    void deletar(String cpf);
}
```

**Benefícios:**
- ✅ Abstração do acesso a dados
- ✅ Permite múltiplas implementações (MySQL, PostgreSQL, etc.)
- ✅ Facilita testes com mocks

#### 3. **DAO (Implementação de Acesso a Dados)**
Implementa a interface Repository com operações CRUD.

```java
public class DoadorDAO implements DoadorRepository {
    
    @Override
    public void adicionar(Doador doador) {
        String sql = "INSERT INTO doadores (...) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DataBaseManager.obtemConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, doador.getCpfDoador());
            stmt.setInt(2, doador.getIdade());
            // ... mais setters ...
            stmt.executeUpdate();
            System.out.println("✅ Doador cadastrado com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar doador: " + e.getMessage(), e);
        }
    }
    
    // ... outros métodos CRUD ...
}
```

**Melhorias:**
- ✅ Implementa interface (OCP, DIP)
- ✅ Try-with-resources para gerenciamento automático de recursos
- ✅ Tratamento de erro com mensagens descritivas
- ✅ Múltiplos métodos CRUD

#### 4. **Service (Lógica de Negócio)**
Centraliza toda a lógica de validação e regras de negócio.

```java
public class DoadorService {
    
    private final DoadorRepository repository;
    
    public DoadorService(DoadorRepository repository) {
        this.repository = repository;
    }
    
    public void cadastrarDoador(Doador doador) throws Exception {
        validarDoador(doador);
        repository.adicionar(doador);
    }
    
    private void validarDoador(Doador doador) {
        if (doador.getIdade() < 16)
            throw new IllegalArgumentException("O voluntário não pode doar se for menor que 16 anos.");
        if (doador.getIdade() > 69)
            throw new IllegalArgumentException("O voluntário não pode doar se for maior que 69 anos.");
        if (doador.getPeso() < 50)
            throw new IllegalArgumentException("O voluntário não pode doar se pesar menos que 50Kg.");
        if (doador.getCpfDoador().length() != 11)
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
    }
}
```

**Benefícios:**
- ✅ Lógica centralizada e reutilizável
- ✅ Fácil de testar isoladamente
- ✅ Exceções descritivas
- ✅ Princípio SRP (Single Responsibility)

#### 5. **Controller (Coordenação)**
Coordena a comunicação entre View e Service.

```java
public class DoadorController {
    
    private final DoadorService doadorService;
    
    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }
    
    public void cadastrarDoador(String cpf, int idade, String sexo, double peso, String nome) throws Exception {
        Doador doador = new Doador(cpf, idade, sexo, peso, nome);
        doadorService.cadastrarDoador(doador);
    }
}
```

**Responsabilidades:**
- ✅ Criar objetos de domínio
- ✅ Delegar lógica ao Service
- ✅ Retornar resultados ou exceções

#### 6. **View (Interface Gráfica)**
Responsável apenas por capturar entrada e apresentar saída.

```java
public class TelaCadastro extends javax.swing.JFrame {
    
    private final DoadorController doadorController;
    
    public TelaCadastro(DoadorController doadorController) {
        initComponents();
        this.doadorController = doadorController;
    }
    
    private void cadastrarDoador() {
        String nome = jTextField1.getText();
        String cpfDoador = jTextField2.getText();
        int idade = Integer.parseInt(jTextField8.getText());
        double peso = Double.parseDouble(jTextField4.getText());
        
        try {
            doadorController.cadastrarDoador(cpfDoador, idade, "M", peso, nome);
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            limparCampos();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

**Melhorias:**
- ✅ Injeção de dependência (recebe controller no construtor)
- ✅ Responsável apenas por UI
- ✅ Tratamento de exceções específicas
- ✅ Código simples e focado

---

## 🏗️ Princípios Aplicados

### SOLID Principles

| Princípio | Aplicação | Exemplo |
|-----------|-----------|---------|
| **SRP** (Single Responsibility) | Cada classe tem uma única razão para mudar | DoadorService valida, DoadorDAO persiste, TelaCadastro apresenta |
| **OCP** (Open/Closed) | Aberta para extensão, fechada para modificação | Interface DoadorRepository permite novas implementações |
| **LSP** (Liskov Substitution) | Subtipos são substituíveis | DoadorDAO implementa DoadorRepository corretamente |
| **ISP** (Interface Segregation) | Interfaces específicas, não genéricas | DoadorRepository com apenas métodos relevantes |
| **DIP** (Dependency Inversion) | Depender de abstrações, não de implementações | DoadorService(DoadorRepository repo) no construtor |

### Clean Code Practices

- ✅ **DRY** (Don't Repeat Yourself): Validação centralizada em Service
- ✅ **KISS** (Keep It Simple, Stupid): Código simples e direto
- ✅ **YAGNI** (You Aren't Gonna Need It): Sem funcionalidades desnecessárias
- ✅ **Try-with-Resources**: Gerenciamento automático de recursos
- ✅ **Nomes Significativos**: Variáveis e métodos com nomes claros
- ✅ **Funções Pequenas**: Métodos com responsabilidade única
- ✅ **Tratamento de Erros**: Exceções descritivas

---

## 🏛️ Arquitetura do Projeto

```
┌─────────────────────────────────────────┐
│         INTERFACE GRÁFICA (VIEW)        │
│  TelaCadastro, TelaChecarDoacoes, etc.  │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│      CAMADA DE COORDENAÇÃO (CONTROLLER) │
│   DoadorController, DoacaoController    │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│    CAMADA DE LÓGICA DE NEGÓCIO (SERVICE)│
│   DoadorService, DoacaoService          │
│   - Validações                          │
│   - Regras de Negócio                   │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│  CAMADA DE ABSTRAÇÃO (REPOSITORY)       │
│  DoadorRepository, DoacaoRepository     │
│  (Interfaces)                           │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│   CAMADA DE ACESSO A DADOS (DAO)        │
│   DoadorDAO, DoacaoDAO                  │
│   - Operações CRUD                      │
│   - Conexão com Banco de Dados          │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│      CAMADA DE DADOS (MODEL)            │
│   Doador, Doacao                        │
│   (Entidades)                           │
└─────────────────────────────────────────┘
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 11 ou superior
- MySQL 5.7 ou superior
- Git

### Passos para Executar

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/A3-Banco-de-Doacoes.git
cd A3-Banco-de-Doacoes
```

2. **Configure o banco de dados:**
```bash
# Crie um banco de dados MySQL
mysql -u root -p
CREATE DATABASE doacao_sangue;
USE doacao_sangue;

# Execute o script SQL (se disponível)
source src/config/database.sql;
```

3. **Configure as credenciais do banco de dados:**
Edite `src/config/DataBaseManager.java`:
```java
private static String usuario = "root";
private static String senha = "sua_senha";
```

4. **Compile o projeto:**
```bash
javac -d bin src/**/*.java
```

5. **Execute a aplicação:**
```bash
java -cp bin telas.TelaInicial
```

---

## 🧪 Testes Unitários

Os testes foram implementados seguindo a metodologia **TDD (Test-Driven Development)**.

### Estrutura de Testes

```
test/
└── config/java/
    ├── DoadorServiceTest.java
    ├── DoadorTest.java
    └── DataBaseManagerTest.java
```

### Exemplos de Testes

#### Teste 1: Validação de Idade
```java
@Test
void idadeDeveSerValida() {
    // Arrange
    Doador doador = new Doador("98765432100", 60, "M", 70.0, "João");

    // Act & Assert
    boolean idadeValida = doador.getIdade() > 16 && doador.getIdade() <= 69;
    assertTrue(idadeValida, "Idade do doador fora do intervalo permitido");
}
```

#### Teste 2: Validação de CPF com Exceção
```java
@Test
void cpfDeveConter11Digitos() {
    // Arrange
    Doador doadorComCpfInvalido = new Doador("123", 30, "M", 70.0, "João");

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        doadorService.cadastrarDoador(doadorComCpfInvalido);
    }, "Deve lançar exceção para CPF com menos de 11 dígitos");
}
```

#### Teste 3: Operação CRUD
```java
@Test
void deveBuscarDoadorPorCpf() {
    // Arrange
    String cpf = "12345678901";
    Doador doadorEsperado = new Doador(cpf, 30, "M", 70.0, "João");
    
    // Act
    doadorDAO.adicionar(doadorEsperado);
    Doador doadorEncontrado = doadorDAO.buscarPorCpf(cpf);

    // Assert
    assertNotNull(doadorEncontrado, "Doador não deveria ser nulo");
    assertEquals(cpf, doadorEncontrado.getCpfDoador(), "CPF deveria ser igual");
}
```



## 📊 Comparação: Antes vs Depois

| Aspecto | Legado | Refatorado | Melhoria |
|---------|--------|-----------|----------|
| **Estrutura** | 1 pacote (telas/) | 5 pacotes (model, controller, service, repository, daoImpl) | Separação clara |
| **Acoplamento** | Alto | Baixo | Flexibilidade |
| **Testabilidade** | Impossível | Fácil | Qualidade |
| **Manutenibilidade** | Difícil | Fácil | Produtividade |
| **Número de Camadas** | 2 (UI + DAO) | 5 (View, Controller, Service, Repository, Model) | Organização |
| **Tratamento de Erros** | Blocos vazios | Exceções descritivas | Depuração |
| **Reutilização** | Baixa | Alta | Código DRY |

---

## 🎓 Conclusão

A refatoração do projeto Banco de Doações demonstrou a importância fundamental do **Clean Code** na manutenção e evolução de software. 

### Principais Aprendizados

1. **Separação de Responsabilidades**: Cada classe tem uma única razão para mudar
2. **Abstração**: Interfaces permitem flexibilidade e testabilidade
3. **Centralização de Lógica**: Evita duplicação e facilita manutenção
4. **Testes Unitários**: Garantem confiança nas mudanças
5. **Trabalho em Equipe**: Colaboração eleva a qualidade do resultado

### Impacto

- ✅ **Redução de Custos**: Menos tempo em manutenção
- ✅ **Aumento de Produtividade**: Código mais fácil de entender
- ✅ **Melhoria de Qualidade**: Código testável e robusto
- ✅ **Facilidade de Extensão**: Novas features sem quebrar código existente

---

## 📚 Referências

### Livros
- **Clean Code** - Robert C. Martin
- **Design Patterns** - Gang of Four
- **Refactoring** - Martin Fowler

### Ferramentas Utilizadas
- **IDE**: IntelliJ IDEA
- **Versionamento**: Git / GitHub
- **Testes**: JUnit 4
- **Banco de Dados**: MySQL

---


## 📄 Licença

Este projeto é fornecido como material educacional para a disciplina de Gestão e Qualidade de Software.

---

**Última atualização:** 27 de Novembro de 2025  
**Status:** ✅ Concluído e Pronto para Apresentação
