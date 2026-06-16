package test.local.veterinario.SeleniumTest;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VeterinarioTest {

    private WebDriver driver;
    private final String URL_BASE = "http://localhost:8080/home";

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Order(1)
    @Test
    public void testarTelaInicialCarregandoDadosExistentesNoBD() {
        // arrange
        String nomePrimeiraLinha = "Conceição Evaristo"; 
        String tituloPagina = "Gerenciador de Veterinários";

        // act
        driver.get(URL_BASE);
        WebElement primeiroNomeTabela = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));

        // assert 
        assertEquals(nomePrimeiraLinha, primeiroNomeTabela.getText().trim());
        assertEquals(tituloPagina, driver.getTitle());
    }

    @Order(2)
    @Test
    public void testarPesquisarVeterinariosNaTabela() throws InterruptedException {
        // arrange
        String nomeVeterinario = "Erica Queiroz Pinto";

        // act
        driver.get(URL_BASE);
        
        WebElement btnConsultar = driver.findElement(By.xpath("//button[contains(text(),'Consultar')]"));
        btnConsultar.click();
        
        WebElement valueNome = driver.findElement(By.id("nome"));
        valueNome.sendKeys(nomeVeterinario);
        
        WebElement btnPesquisar = driver.findElement(By.cssSelector("button[type='submit']"));
        btnPesquisar.click(); 
        
        Thread.sleep(1000); // pausa para aguardar a atualização do filtro na tela

        WebElement primeiroResultado = driver.findElement(By.xpath("//tbody/tr[1]/td[2]"));

        // assert 
        assertEquals(nomeVeterinario, primeiroResultado.getText().trim());
    }

    @Order(3)
    @Test 
    public void testarBotaoAdicionarCadastrarFuncionario() throws InterruptedException {
        // arrange
        String nomeVeterinario = "Rubeo Hagrid";
        String emailVeterinario = "hagrid.rubeo@gmail.com";
        String especialidadesVeterinario = "Animais mágicos";
        String salarioVeterinario = "2500.00";
          
        // act
        driver.get(URL_BASE);
        
        WebElement btnAdicionar = driver.findElement(By.xpath("//button[contains(.,'Adicionar')]"));
        btnAdicionar.click();
          
        driver.findElement(By.id("nome")).sendKeys(nomeVeterinario);
        driver.findElement(By.id("inputEmail")).sendKeys(emailVeterinario);
        driver.findElement(By.id("inputEspecialidade")).sendKeys(especialidadesVeterinario);
        driver.findElement(By.id("inputSalario")).sendKeys(salarioVeterinario);

        WebElement btnCadastrar = driver.findElement(By.xpath("//button[contains(.,'Cadastrar')]"));
        btnCadastrar.click();
        
        Thread.sleep(1000); // pausa para o banco processar o insert e redirecionar
        
        // assert 
        assertTrue(driver.getPageSource().contains(nomeVeterinario), "O veterinário cadastrado deveria aparecer na listagem.");
    }

    @Order(4)
    @Test
    public void testarAlterarOsDadosDeUmVeterinario() throws InterruptedException {
        // arrange
        String nomeAlterado = "Leonardo Andrade Modificado";
        
        // act
        driver.get(URL_BASE);
        
        WebElement btnAlterar = driver.findElement(By.xpath("//tbody/tr[last()]/td[5]/a[1]"));
        btnAlterar.click();

        WebElement valorNome = driver.findElement(By.id("nome"));
        valorNome.clear();
        valorNome.sendKeys(nomeAlterado);

        WebElement btnAtualizar = driver.findElement(By.xpath("//button[normalize-space()='Atualizar']"));
        btnAtualizar.click();

        Thread.sleep(1000);

        // assert 
        assertTrue(driver.getPageSource().contains(nomeAlterado));
    }

    @Order(5)
    @Test
    public void testarExcluirCadastroDeUmVeterinario() throws InterruptedException {
        // arrange
        driver.get(URL_BASE);
        WebElement ultimoNomeTabela = driver.findElement(By.xpath("//tbody/tr[last()]/td[2]"));
        String nomeVeterinario = ultimoNomeTabela.getText().trim();
        
        // act
        WebElement btnDeletar = driver.findElement(By.xpath("//tbody/tr[last()]/td[5]/a[2]"));
        btnDeletar.click();
    
        Thread.sleep(1000);
    
        // assert 
        assertFalse(driver.getPageSource().contains(nomeVeterinario), "O nome do veterinário não deveria mais estar na página.");
    }
}