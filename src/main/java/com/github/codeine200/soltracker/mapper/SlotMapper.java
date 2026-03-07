package com.github.codeine200.soltracker.mapper;

import com.github.codeine200.soltracker.dto.response.SlotResponseDto;
import org.mapstruct.Mapper;
import com.github.codeine200.soltracker.remote.response.SlotRemoteResponse;

@Mapper(componentModel = "spring")
public interface SlotMapper {

    SlotResponseDto toDto(SlotRemoteResponse remoteResponse);
}
