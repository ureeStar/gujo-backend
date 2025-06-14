package com.uswchangup.backup.gujo_Parkdaejang.controller

import com.uswchangup.backup.gujo_Parkdaejang.dto.FormDto
import com.uswchangup.backup.gujo_Parkdaejang.entity.FormEntity
import com.uswchangup.backup.gujo_Parkdaejang.repository.FormRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class FormController(
    private val formRepository: FormRepository
) {

    // 1) 신청 저장 (로그인 없이 가능)
//    @CrossOrigin(origins = ["http://127.0.0.1:5500"], allowCredentials = "true")
    @PostMapping("/apply")
    fun saveForm(@RequestBody formDto: FormDto): ResponseEntity<String> {
        val entity: FormEntity = formDto.toEntity()
        formRepository.save(entity)
        return ResponseEntity.ok("신청이 저장되었습니다.")
    }

    // 2) 관리자만 목록 조회
    @GetMapping("/admin/applications")
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllApplications(): ResponseEntity<List<FormDto>> {
        println("✅ 인증 객체: ${SecurityContextHolder.getContext().authentication}")

        // ⭐ 여기서 현재 SecurityContextHolder 상태를 확인!
        val auth = SecurityContextHolder.getContext().authentication
        println("현재 사용자 이름: ${auth?.name}")
        println("현재 권한 목록: ${auth?.authorities}")

        val all = formRepository.findAll()
        val dtoList = all.map { FormDto.fromEntity(it) }
        return ResponseEntity.ok(dtoList)
    }

    @DeleteMapping("/admin/applications/{id}")
    fun deleteApplication(@PathVariable id: Long): ResponseEntity<String> {
        val exists = formRepository.existsById(id)
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 문의가 존재하지 않습니다.")
        }

        formRepository.deleteById(id)
        return ResponseEntity.ok("삭제 완료")
    }
}