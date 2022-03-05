package business.model;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

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

	/**
	 * Mantiene la bidireccionalidad de la relación N:M entre banda organizada y delincuente
	 */
	public static class Afiliarse
	{
		private Afiliarse() {
		}

		/**
		 * Asocia un nuevo registro de afiliación entre las entidades
		 * banda organizada y delincuente pasadas por parametro.
		 *
		 * @param bandaOrganizada entidad a tratar, nunca {@code null}.
		 * @param delincuente entidad a tratar, nunca {@code null}.
		 * @return el registro de asociacion creado entre ambas entidades.
		 */
		public static AfiliacionBandaDelincuente link(
				final BandaOrganizada bandaOrganizada, final Delincuente delincuente) {
			Objects.requireNonNull(bandaOrganizada, "`bandaOrganizada` must be non-null");
			Objects.requireNonNull(delincuente, "`delincuente` must be non-null");
			final AfiliacionBandaDelincuente afiliacion = new AfiliacionBandaDelincuente(
					bandaOrganizada, delincuente);
			delincuente._getAfiliaciones().add(afiliacion);
			bandaOrganizada._getAfiliaciones().add(afiliacion);
			return afiliacion;
		}

		/**
		 * Desasocia todos los registros de afiliación que coincidan con la tupla
		 * banda organizada - delincuente provistos.
		 *
		 * @param bandaOrganizada entidad a tratar, nunca {@code null}.
		 * @param delincuente entidad a tratar, nunca {@code null}.
		 * @return {@code true} si realmente se realizo alguna operación de desasociación.
		 */
		public static boolean unlink(
				final BandaOrganizada bandaOrganizada, final Delincuente delincuente) {
			Objects.requireNonNull(bandaOrganizada, "`bandaOrganizada` must be non-null");
			Objects.requireNonNull(delincuente, "`delincuente` must be non-null");
			final Predicate<AfiliacionBandaDelincuente> filter =
					afiliacion -> afiliacion.same(bandaOrganizada, delincuente);
			boolean dirty = false;
			dirty |= disposeAll(delincuente._getAfiliaciones(), filter);
			dirty |= disposeAll(bandaOrganizada._getAfiliaciones(), filter);
			return dirty;
		}

		private static <T extends AfiliacionBandaDelincuente> boolean disposeAll(
				final Iterable<T> afiliaciones, final Predicate<T> filter) {
			Objects.requireNonNull(filter, "`filter` must be non-null");
			boolean dirty = false;
			for (final Iterator<T> it = afiliaciones.iterator(); it.hasNext(); ) {
				final T afiliacion = it.next();
				if (filter.test(afiliacion)) {
					// dispose preventing memory leaks
					afiliacion.setBandaOrganizada(null);
					afiliacion.setDelincuente(null);
					it.remove();
					dirty = true;
				}
			}
			return dirty;
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
