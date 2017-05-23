package com.example.service;

import com.example.dto.BoardDTO;
import com.example.dto.ColonneKanbanDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;


public interface ColonneKanbanService {

	ColonneKanbanDTO createColonneKanban(ColonneKanbanDTO colonneKanbanDTO, Long boardId);

	ColonneKanbanDTO updateColonneKanban(Long id, ColonneKanbanDTO colonne);

    void deleteColonneKanban(Long id);
}