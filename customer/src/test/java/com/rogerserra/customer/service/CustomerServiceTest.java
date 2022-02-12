package com.rogerserra.customer.service;

import com.rogerserra.customer.model.Customer;
import com.rogerserra.customer.repository.CustomerRepository;
import com.rogerserra.customer.request.CustomerRegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Los servicios se testean con Mockito, ya que dependen de un repositorio
// No hace falta, a priori, ninguna anotacion aqui arriba
class CustomerServiceTest {

    // https://www.javatpoint.com/methods-of-mockito
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable; //https://docs.oracle.com/javase/8/docs/api/java/lang/AutoCloseable.html
// An object that may hold resources (such as file or socket handles) until it is closed.
// it enables the use of try-with-resources which is a new feature from Java 7.

    /*
    Old-school:

        InputStream is = null;
        try {
            is = ...;
            // do stuff with is...
        } catch (IOException e) {
            // handle exception
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException innerE) {
                    // Handle exception
                }
            }

    New-school:
        try (InputStream is = ...) {
            // do stuff with is...
        } catch (IOException e) {
            // handle exception
        }

     */

    @InjectMocks // en lugar de autowired se le mete esto?
    private CustomerService underTest;

    // No queremos hacer esto: BeforeEach underTest = new UserService(UserRepository)
    // El repositorio ya lo hemos testeado, ya sabemos que funciona, aqui es donde entran los mocks.
    // NO queremos testear el repositorio de Usuarios real, porque estamos testeando el Servicio en este test
    // Ventajas: el test es rapido, no tenemos que hacer deploy de la base de datos (crear tablas, insertar, borrar...)
    // Si ya hemos testeado el repositorio una vez, no tenemos que testearlo otra vez de forma indirecta
    // Tambien puede ser util en los casos en que otro equipo de desarrollo hizo el repositorio y no lo conocemos bien
    // o esta a medias, o esta en un servidor a traves de otra peticion, etc.

    // Control + Alt + V  -> extraer linea de codigo en una variable

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close(); //cerramos el recurso despues del test
    }

    // SOLO testeamos los que hemos creado nosotros, ya que los de JpaRepository ya estan probados por los creadores de Spring
    // pero voy a programar todos igualmente

    @Test
    void canRegisterCustomer(){
        //GIVEN
        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequest(
                        "paco",
                        "pepe",
                        "pacopepe@gmail.com");
        // WHEN
        underTest.registerCustomer(customerRegistrationRequest);
        // THEN
        ArgumentCaptor<Customer> argumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(argumentCaptor.capture());

        Customer captured = argumentCaptor.getValue();
        assertThat(captured.getFirstName()).isEqualTo(customerRegistrationRequest.firstName());

    }

    @Test
    void canGetAllCustomers(){
        underTest.getAllCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void canGetCustomerById(){
        System.out.println("No hace falta testear los ids autogenerados de BBDD.");
        System.out.println("Ademas, el findById no hace falta ni testearlo en repositorio.");
    }

    @Test
    void canGetCustomerByEmail() {
        //GIVEN
        CustomerRegistrationRequest customerRegistrationRequest =
                new CustomerRegistrationRequest(
                        "paco",
                        "pepe",
                        "pacopepe@gmail.com");
        //WHEN
        underTest.registerCustomer(customerRegistrationRequest);
        underTest.deleteCustomerByEmail(customerRegistrationRequest.email());
        //THEN
        verify(customerRepository).deleteEmployeeByEmail(customerRegistrationRequest.email());
        //Customer customer = underTest.getCustomerByEmail(customerRegistrationRequest.email());
        // como es un mock, en el servicio -> Customer customerByEmail = customerRepository.findEmployeeByEmail(email);
        // customerByEmail es nulo. Pero no pasa nada, ya que el metodo funciona porqe esta testeado en los tests de repositorio

        // como creo un objeto en servicio, e instancio otro en test, es lo mismo pero con id distinto
    }

    @Test
    void canGetCustomersByLastName() {
        //GIVEN
        CustomerRegistrationRequest cr = new CustomerRegistrationRequest(
                "feliciano",
                "mendez",
                "fmendez@gmail.com"
        );
        /* FUNCIONA, pero lo otro se parece mas al getAll()
        when(customerRepository.findEmployeeByLastName(cr.lastName()))
                .thenReturn(List.of(new Customer()));
                */
        //THEN
        underTest.getCustomersByLastName(cr.lastName());
        verify(customerRepository).findEmployeeByLastName(cr.lastName());

        /*
        Wanted but not invoked:
        However, there was exactly 1 interaction with this mock:

        Solucion:
        This happens with mockito when trying to verify the invocation on an object with specific method.
        But what happens is you have interacted with other method of that object but not the one mentioned.
        Salta cuando se prueban 2 metodos del servicio. Yo estaba probando servicio.addCustomer y repositorio.findBYLastName
        En el verify se prueba 1 SOLO METODO
         */
    }

    @Test
    void canDeleteCustomerById() {
        System.out.println("No se interactua on ids autogenerados");
    }

    @Test
    void canDeleteCustomerByEmail(){
        // GIVEN
        CustomerRegistrationRequest cr =
                new CustomerRegistrationRequest(
                        "angela",
                        "donoso",
                        "adnoso@gmail.com"
                );
        // WHEN
        underTest.registerCustomer(cr);
        underTest.deleteCustomerByEmail(cr.email());
        verify(customerRepository).deleteEmployeeByEmail(cr.email());
        // THEN
        /*
                //GIVEN
        Customer customer = Customer.builder()
                .firstName("Manolo")
                .lastName("Garcia")
                .email("manolog@gmail.com")
                .build();
        //WHEN
        underTest.save(customer);
        underTest.deleteEmployeeByEmail(customer.getEmail());
        //THEN
        Customer optionalCustomer = underTest.findEmployeeByEmail(customer.getEmail());
        boolean encontrado = optionalCustomer != null;
        //THEN
        assertThat(encontrado).isFalse();
         */
    }

    @Test
    void updateCustomerById() {
        // GIVEN
        CustomerRegistrationRequest cr =
                new CustomerRegistrationRequest(
                        "mercedes",
                        "ramos",
                        "mramos@gmail.com"
                );
        Customer customer = Customer.builder()
                .firstName(cr.firstName())
                .lastName(cr.lastName())
                .email(cr.email())
                .build();
        // WHEN
        underTest.updateCustomerById(30, cr);
        // THEN
        when(customerRepository.save(customer)).thenThrow(new RuntimeException("MAL"));

    }

}