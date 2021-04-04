package kr.tripamigo.tripamigo.dto.paging;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Builder
public class PagingDTO {

    public static int BLOCK_SIZE_M = 5;
    public static int BLOCK_SIZE_L = 10;

    private final int boardCount;
    private final int pageCount; // = end page
    private final int currentPage;
    private final int pageSize;

    // block : page 단위
    private final int blockSize;
    private final int blockCount;
    private final int currentBlock;
    private final int currentBlockFirstPage;
    private final int currentBlockEndPage;
    private final List<Integer> block; // page list

    public static PagingDTO getPagingDTO(Pageable pageable, int boardCount, int blockSize) {
        int pageSize = pageable.getPageSize();
        int pageCount = (boardCount - 1) / pageSize;
        int currentBlock = pageable.getPageNumber() / blockSize;
        int currentBlockFirstPage = currentBlock * blockSize;
        int currentBlockEndPage = Math.min(currentBlock * blockSize + (blockSize - 1), pageCount);

        return PagingDTO.builder()
                .boardCount(boardCount)
                .pageCount(pageCount)
                .currentPage(pageable.getPageNumber())
                .pageSize(pageSize)
                .blockSize(blockSize)
                .blockCount(pageCount / blockSize)
                .currentBlock(currentBlock)
                .currentBlockFirstPage(currentBlockFirstPage)
                .currentBlockEndPage(currentBlockEndPage)
                .block(IntStream.range(currentBlockFirstPage, currentBlockEndPage + 1).boxed().collect(Collectors.toList()))
                .build();
    }

}
