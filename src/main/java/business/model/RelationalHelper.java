package business.model;

import lombok.NonNull;

public class RelationalHelper
{
	private RelationalHelper() {
	}

	public static class BancoSucursal
	{
		private BancoSucursal() {
		}

		public static void link(final @NonNull Banco banco, final @NonNull Sucursal sucursal) {
			sucursal.setBanco(banco);
			banco._getSucursales().add(sucursal);
		}

		public static void unlink(final @NonNull Banco banco, final @NonNull Sucursal sucursal) {
			banco._getSucursales().remove(sucursal);
			sucursal.setBanco(null);
		}
	}

	public static class AsignarVigilancia
	{
		private AsignarVigilancia() {
		}

		public static void link(final @NonNull Sucursal sucursal, final @NonNull Contrato contrato) {
			contrato.setSucursal(sucursal);
			sucursal._getContratos().add(contrato);
		}

		public static void unlink(final @NonNull Vigilante vigilante, final @NonNull Contrato contrato) {
			vigilante._getContratos().remove(contrato);
			contrato.setVigilante(null);
		}
	}

	public static class ContratarVigilante
	{
		private ContratarVigilante() {
		}

		public static void link(final @NonNull Vigilante vigilante, final @NonNull Contrato contrato) {
			contrato.setVigilante(vigilante);
			vigilante._getContratos().add(contrato);
		}

		public static void unlink(final @NonNull Vigilante vigilante, final @NonNull Contrato contrato) {
			vigilante._getContratos().remove(contrato);
			contrato.setVigilante(null);
		}
	}

	public static class Delinquir
	{
		private Delinquir() {
		}

		public static void link(final @NonNull Delincuente delincuente, final @NonNull Atraco atraco) {
			atraco.setDelincuente(delincuente);
			delincuente._getAtracos().add(atraco);
		}

		public static void unlink(final @NonNull Delincuente delincuente, final @NonNull Atraco atraco) {
			delincuente._getAtracos().remove(atraco);
			atraco.setDelincuente(null);
		}
	}

	public static class SucursalAtracada
	{
		private SucursalAtracada() {
		}

		public static void link(final @NonNull Sucursal sucursal, final @NonNull Atraco atraco) {
			atraco.setSucursal(sucursal);
			sucursal._getAtracos().add(atraco);
		}

		public static void unlink(final @NonNull Sucursal sucursal, final @NonNull Atraco atraco) {
			sucursal._getAtracos().remove(atraco);
			atraco.setSucursal(null);
		}
	}

	public static class Afiliarse
	{
		private Afiliarse() {
		}

		public static void link(final @NonNull BandaOrganizada banda, final @NonNull Delincuente delincuente) {
			delincuente.setBandaOrganizada(banda);
			banda._getDelincuentes().add(delincuente);
		}

		public static void unlink(final @NonNull BandaOrganizada banda, final @NonNull Delincuente delincuente) {
			banda._getDelincuentes().remove(delincuente);
			delincuente.setBandaOrganizada(null);
		}
	}

	public static class Juzgar
	{
		private Juzgar() {
		}

		public static void link(final @NonNull Juez juez, final @NonNull Atraco atraco) {
			atraco.setJuez(juez);
			juez._getAtracos().add(atraco);
		}

		public static void unlink(final @NonNull Juez juez, final @NonNull Atraco atraco) {
			juez._getAtracos().remove(atraco);
			atraco.setJuez(null);
		}
	}

}
