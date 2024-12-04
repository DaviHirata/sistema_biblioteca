package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Reserva;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reserva")
@Tag(name = "Reservas", description = "Path relacionado para controlar as reservas de livros")
public class ReservaController {
    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @Operation(summary = "Fazer uma nova reserva", description = "Cria uma nova reserva de livro no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public ResponseEntity salvar(@RequestBody @Valid Reserva reserva, UriComponentsBuilder uriBuilder) {
        this.reservaService.salvarReserva(reserva);
        URI uri = uriBuilder.path("/reserva/uuid/{uuid}").buildAndExpand(reserva.getUuid()).toUri();
        return ResponseEntity.created(uri).body(reserva);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar reservas", description = "Mostra todas as reservas do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<Reserva> listar() {
        return this.reservaService.listarReservas();
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar por uma reserva específica", description = "Retorna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public Reserva buscarPorUUID(@PathVariable String uuid) {
        return this.reservaService.getReservaUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar uma reserva", description = "Método para realizar alterações das informações de uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atualização feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity atualizar(@RequestBody Reserva reserva) {
        this.reservaService.atualizarReservaUuid(reserva);
        return ResponseEntity.ok(reserva);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar uma reserva", description = "Método para remover um registro de reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exclusão da reserva bem sucedida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity deletar(@PathVariable String uuid) {
        this.reservaService.excluirReservaUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
