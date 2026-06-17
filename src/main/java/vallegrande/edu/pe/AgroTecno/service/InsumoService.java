package vallegrande.edu.pe.AgroTecno.service;

import java.util.List;
import java.util.Optional;

import vallegrande.edu.pe.AgroTecno.model.Insumo;

public interface InsumoService {

    // ⚙️🔍 Listar Todos
    List<Insumo> findAll();
    // ⚙️🔍 Listar por Estado
    List<Insumo> findByEstado(Boolean estado);
    // ⚙️🔍 Listar por ID
    Optional<Insumo> findById(Integer id);
    // ⚙️✅ Registrar
    Insumo save(Insumo insumo);
    // ⚙️✏️ Actualizar
    Insumo update(Insumo insumo);
    // ⚙️❌ Eliminar lógico
    Insumo delete(Integer id);
    // ⚙️♻️ Restaurar lógico
    Insumo restore(Integer id);
}