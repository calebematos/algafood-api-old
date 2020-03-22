package com.calebematos.algafood.infrastructure.service.report;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.calebematos.algafood.domain.filter.VendaDiariaFilter;
import com.calebematos.algafood.domain.service.VendaQueryService;
import com.calebematos.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService {

	@Autowired
	private VendaQueryService vendaQueryService;

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

		try {
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", LocaleContextHolder.getLocale());

			var vendas = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(vendas);

			var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}
