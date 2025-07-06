-- Profesores
INSERT INTO profesor (id, nombres, apellidos, fecha_ingreso)
VALUES (1, 'Pedro', 'Martínez', '2021-02-01');

-- Materias
INSERT INTO materia (id, nombre_materia, profesor_id)
VALUES (1, 'Matemática I', 1);

-- Alumnos
INSERT INTO alumno (id, nombres, apellidos, correo, fecha_ingreso, direccion, telefono)
VALUES (1, 'alumno', 'demo','alumno@demo.com', '2023-01-15', 'San Salvador', '7000-0000');

-- AlumnoMateria
INSERT INTO alumno_materia (id, alumno_id, materia_id, ciclo, nota_final)
VALUES (1, 1, 1, '01-2024', 8.7);
