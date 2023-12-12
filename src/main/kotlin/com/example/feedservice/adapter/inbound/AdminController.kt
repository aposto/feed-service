package com.example.feedservice.adapter.inbound

import arrow.core.getOrElse
import com.example.feedservice.adapter.inbound.dto.NewsResponse
import com.example.feedservice.adapter.inbound.dto.PageRequest
import com.example.feedservice.adapter.inbound.dto.PageResponse
import com.example.feedservice.adapter.outbound.document.docs.makePageId
import com.example.feedservice.application.port.inbound.FeedNewsUseCase
import com.example.feedservice.application.port.inbound.NewsCommand
import com.example.feedservice.application.port.inbound.PageCommand
import com.example.feedservice.domain.CommandResult
import com.example.feedservice.domain.NewsBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin/v1/")
class AdminController(
    val pageCommand: PageCommand,
    val feedNewsUseCase: FeedNewsUseCase,
    val newsCommand: NewsCommand,
){
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "뉴스피드 지역/학교별 페이지 생성")])
    @PostMapping("/page")
    @ResponseBody
    suspend fun createPage(@RequestBody page: PageRequest, response: ServerHttpResponse): PageResponse? {
        val id = makePageId(page.region, page.schoolName)
        return pageCommand.createPage(id, page.region, page.schoolName)
            .map { PageResponse(it, page.region, page.schoolName) }
            .getOrElse {
                response.statusCode = HttpStatus.BAD_REQUEST
                null
            }
    }

    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "뉴스 피드 생성")])
    @PostMapping("/news")
    @ResponseBody
    suspend fun feedNews(@RequestBody data: NewsBody): NewsResponse? {
        return feedNewsUseCase.feedNews("0", data)
            .map { NewsResponse(id = it.id, error = null) }
            .onLeft { NewsResponse(id = -1, error = it.message) }
            .getOrNull()
    }

    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "게시된 뉴스 삭제")])
    @DeleteMapping("/news/{newsId}")
    suspend fun deleteNews(@PathVariable("newsId") newsId: Long): CommandResult {
        // 모든 처리는 인증 되었다고 가정
        return newsCommand.deleteNews("0", newsId)
    }

    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "게시된 뉴스 수정")])
    @PutMapping("/news/{newsId}")
    suspend fun updateNews(@PathVariable("newsId") newsId: Long, @RequestBody data: NewsBody): CommandResult {
        // 모든 처리는 인증 되었다고 가정
        return newsCommand.updateNews("0", newsId, data)
    }
}
