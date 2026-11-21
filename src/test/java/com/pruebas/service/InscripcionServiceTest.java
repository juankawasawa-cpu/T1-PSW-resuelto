package com.pruebas.service;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository repository;

    @InjectMocks
    private InscripcionService service;

    @Test
    void inscribirEstudianteCorrectamente() {
        Inscripcion solicitud = new Inscripcion();
        solicitud.setCodigoEstudiante("I2024077");
        solicitud.setCursoId(3L);

        Inscripcion guardada = new Inscripcion();
        guardada.setId(1L);
        guardada.setCodigoEstudiante("I2024077");
        guardada.setCursoId(3L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024077", 3L))
                .thenReturn(false);
        when(repository.save(solicitud)).thenReturn(guardada);

        Inscripcion resultado = service.inscribir(solicitud);

        assertNotNull(resultado);
        assertEquals("I2024077", resultado.getCodigoEstudiante());
        assertEquals(3L, resultado.getCursoId());
        verify(repository, times(1)).save(solicitud);
    }

    @Test
    void rechazarInscripcionDuplicada() {
        Inscripcion solicitud = new Inscripcion();
        solicitud.setCodigoEstudiante("I2024077");
        solicitud.setCursoId(3L);

        when(repository.existsByCodigoEstudianteAndCursoId("I2024077", 3L))
                .thenReturn(true);

        RuntimeException excepcion = assertThrows(
                RuntimeException.class,
                () -> service.inscribir(solicitud)
        );

        assertEquals("Estudiante ya inscrito", excepcion.getMessage());
        verify(repository, never()).save(any(Inscripcion.class));
    }

    @Test
    void cancelarInscripcionExistente() {
        Inscripcion existente = new Inscripcion();
        existente.setId(1L);
        existente.setCodigoEstudiante("I2024077");
        existente.setCursoId(3L);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));

        assertDoesNotThrow(() -> service.cancelar(1L));

        verify(repository, times(1)).delete(existente);
    }
}
