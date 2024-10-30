package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Reserva;
import br.csi.sistema_biblioteca.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reserva")
public class ReservaController {
    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public void salvar(@RequestBody Reserva reserva) {
        this.reservaService.salvarReserva(reserva);
    }

    @GetMapping("/listar")
    public List<Reserva> listar() {
        return this.reservaService.listarReservas();
    }

    @GetMapping("/uuid/{uuid}")
    public Reserva reserva(@PathVariable String uuid) {
        return this.reservaService.getReservaUUID(uuid);
    }

    @PutMapping("/uuid")
    public void atualizar(@RequestBody Reserva reserva) {
        this.reservaService.atualizarReservaUuid(reserva);
    }

    @DeleteMapping("/{uuid}")
    public void deletar(@PathVariable String uuid) {
        this.reservaService.excluirReservaUuid(uuid);
    }
}
