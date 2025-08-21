//package com.github.DKowalski25._min.unit.timeblock;
//
//import com.github.DKowalski25._min.dto.taskblock.TimeBlockRequestDTO;
//import com.github.DKowalski25._min.dto.taskblock.TimeBlockUpdateDTO;
//import com.github.DKowalski25._min.models.TimeBlock;
//import com.github.DKowalski25._min.models.TimeType;
//
//import com.github.DKowalski25._min.repository.timeblock.TimeBlockRepository;
//import com.github.DKowalski25._min.service.timeblock.TimeBlockService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class TimeBlockServiceTest {
//
//    @Mock
//    private TimeBlockRepository repository;
//
//    @InjectMocks
//    private TimeBlockService service;
//
//    private TimeBlock timeBlock;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        timeBlock = new TimeBlock();
//        timeBlock.setId(UUID.randomUUID());
//        timeBlock.setType(TimeType.DAY);
//    }
//
//    @Test
//    void createTimeBlock_shouldReturnSaved() {
//        TimeBlockRequestDTO dto = new TimeBlockRequestDTO(TimeType.DAY);
//        when(repository.save(any())).thenReturn(timeBlock);
//
//        TimeBlock result = service.createDefaultTimeBlocks(dto);
//
//        assertNotNull(result);
//        assertEquals(TimeType.DAY, result.getType());
//        verify(repository).save(any());
//    }
//
//    @Test
//    void getTimeBlockById_found() {
//        when(repository.findById(timeBlock.getId())).thenReturn(Optional.of(timeBlock));
//
//        TimeBlock result = service.getTimeBlockById(timeBlock.getId());
//
//        assertEquals(timeBlock.getId(), result.getId());
//    }
//
//    @Test
//    void getTimeBlockById_notFound() {
//        UUID id = UUID.randomUUID();
//        when(repository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> service.getTimeBlockById(id));
//    }
//
//    @Test
//    void updateTimeBlock_shouldUpdateType() {
//        TimeBlockUpdateDTO dto = new TimeBlockUpdateDTO(TimeType.BREAK);
//        when(repository.findById(timeBlock.getId())).thenReturn(Optional.of(timeBlock));
//
//        service.updateTimeBlock(timeBlock.getId(), dto);
//
//        assertEquals(TimeType.BREAK, timeBlock.getType());
//        verify(repository).save(timeBlock);
//    }
//
//    @Test
//    void deleteTimeBlock_shouldCallRepository() {
//        service.deleteTimeBlock(timeBlock.getId());
//        verify(repository).deleteById(timeBlock.getId());
//    }
//}
