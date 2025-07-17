package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ArrondissementDao;
import com.natha.dev.Dao.DepartementDao;
import com.natha.dev.Dto.ArrondissementDto;
import com.natha.dev.IService.ArrondissementIService;
import com.natha.dev.Model.Arrondissement;
import com.natha.dev.Model.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArrondissmentImpl implements ArrondissementIService {

    @Autowired
    private ArrondissementDao arrondissementDao;

    @Autowired
    private DepartementDao departementDao;

    @Override
    public ArrondissementDto save(ArrondissementDto arrondissementDto) {
        Arrondissement arrondissement = convertToEntity(arrondissementDto);
        Arrondissement savedArrondissement = arrondissementDao.save(arrondissement);
        return convertToDto(savedArrondissement);
    }

    @Override
    public Optional<ArrondissementDto> findById(Long id) {
        return arrondissementDao.findById(id).map(this::convertToDto);
    }

    @Override
    public List<ArrondissementDto> findAllDepartement() {
        // Rete pou aplike si sa nesesè
        return Collections.emptyList();
    }

    @Override
    public List<ArrondissementDto> findAll() {
        return arrondissementDao.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        arrondissementDao.deleteById(id);
    }

    @Override
    public List<ArrondissementDto> getAll() {
        return findAll();
    }

    @Override
    public List<ArrondissementDto> getByDepartementId(Long id) {
        return arrondissementDao.findByDepartementId(id).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArrondissementDto> findByDepartementIdWithProjects(Long departementId) {
        return arrondissementDao.findByDepartementIdWithProjects(departementId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ArrondissementDto convertToDto(Arrondissement arrondissement) {
        ArrondissementDto dto = new ArrondissementDto();
        dto.setId(arrondissement.getId());
        dto.setName(arrondissement.getName());
        if (arrondissement.getDepartement() != null) {
            dto.setDepartementId(arrondissement.getDepartement().getId());
            dto.setNameDepartement(arrondissement.getDepartement().getName());
        }
        return dto;
    }

    private Arrondissement convertToEntity(ArrondissementDto dto) {
        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setId(dto.getId());
        arrondissement.setName(dto.getName());

        if (dto.getDepartementId() != null) {
            Departement departement = departementDao.findById(dto.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Département non trouvé avec l'id: " + dto.getDepartementId()));
            arrondissement.setDepartement(departement);
        } else {
            throw new RuntimeException("L'ID du département est requis pour créer un arrondissement.");
        }
        return arrondissement;
    }
}
