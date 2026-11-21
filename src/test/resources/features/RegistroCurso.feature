Feature: Registro de cursos
  Como administrador académico
  Quiero registrar cursos
  Para mantener actualizada la oferta académica

  Scenario: Registro de curso válido
    Given que el servicio de cursos está disponible
    When registro el curso "Selenium Avanzado" con precio 400.0 y 20 vacantes
    Then la respuesta del registro debe ser 200
    And el nombre del curso registrado debe ser "Selenium Avanzado"

  Scenario: Registro con precio inválido
    Given que el servicio de cursos está disponible
    When registro el curso "Curso con precio inválido" con precio 0.0 y 20 vacantes
    Then la respuesta del registro debe ser un error
