package ui;

import static java.lang.String.format;
import static util.Strings.BOX_VERTICAL_LINE;
import static util.Strings.EMPTY;
import static util.Strings.SPACE;
import static util.Strings.isBlank;
import static util.Strings.isNotBlank;
import static util.Strings.repeat;
import static util.console.Console.printf;
import static util.console.Console.println;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import business.model.Banco;
import business.model.Delincuente;
import lombok.NonNull;
import util.Strings;
import util.console.Console;

public abstract class ConsoleUI {

	public static void printHeader(@NonNull String prompt)
	{
		int len = prompt.length();
		String prefix = repeat(SPACE, 8);
		String suffix = repeat(SPACE, 80 / 2 - len);
		println(EMPTY);
		println("╔══════════════════════════════════════════════════════════════════════════════╗");
		printf( "╠═══╣►►►►►►►►►► %s%s%s ◄◄◄◄◄◄◄◄◄◄╠═══╣%n", prefix, prompt, suffix);
		println("╚══════════════════════════════════════════════════════════════════════════════╝");
	}

	public static final Consumer<Object> boxedPrinting = item -> println(boxedRowing(3, 80 - 3 * 2 - 2).apply(item));

	public static Function<Object, String> boxedRowing(int gap, int boxWidth)
	{
		return row -> {
			final String str = String.valueOf(row);
			final String gaps = repeat(SPACE, gap);
			final int fill = boxWidth - str.length() - 2; // 2 spaces in format string
			final String fills = repeat(SPACE, Math.max(0, fill));
			return format("%s%s %s%s %s%s", gaps, BOX_VERTICAL_LINE, str, fills, fill < 0 ? EMPTY : BOX_VERTICAL_LINE, gaps);
		};
	}

	public static boolean printBancos(final Collection<Banco> bancos)
	{
		return printBancos(bancos, () -> "INFO :: Aún no hay bancos");
	}

	public static boolean printBancos(
			final Collection<Banco> bancos,
			final @NonNull Supplier<CharSequence> emptyMessage)
	{
		return printBox(bancos, "BANCOS", emptyMessage, false);
	}

	public static void printBoxTop()
	{
		println(EMPTY);
		println("                         ╔════════════════════════════╗                         ");
	}

	public static boolean printBox(
			final Collection<?> items,
			final @NonNull Supplier<CharSequence> captionMessage,
			final @NonNull Supplier<CharSequence> emptyMessage,
			final boolean showTotal)
	{
		if (items == null || items.isEmpty()) {
			printf("%n%n%s.%n%n", emptyMessage.get());
			return true;
		}
		printBoxTop();
		printf("  ◄╔═════════════════════╝► %s ◄╚═════════════════════╗►  %n",
				Strings.center(captionMessage.get(), 24));
		items.forEach(boxedPrinting);
		printBoxBottom();
		if (showTotal) {
			printf("     Total de %s: %2d", captionMessage.get(), items.size());
		}
		return false;
	}

	public static boolean printBox(
			final Collection<?> items,
			final @NonNull CharSequence captionMessage,
			final @NonNull Supplier<CharSequence> emptyMessage,
			final boolean showTotal)
	{
		return printBox(items, () -> captionMessage, emptyMessage, showTotal);
	}

	public static boolean printBox(
			final Collection<?> items,
			final @NonNull CharSequence captionMessage,
			final @NonNull CharSequence emptyMessage,
			final boolean showTotal)
	{
		return printBox(items, () -> captionMessage, () -> emptyMessage, showTotal);
	}

	public static void printBoxBottom()
	{
		println("  ◄╚═══════════════════════════════╣► ♦♦♦♦ ◄╠═══════════════════════════════╝►  ");
	}


	public static String readString(
			String prompt,
			String requiredErrorMsg,
			int maxLength,  String maxLengthErrorMsg)
	{
		boolean validateRequired = requiredErrorMsg != null;
		boolean validateMaxLength = maxLength > 0;
		String valor = Console.readString(format("   %s%s", prompt, validateRequired ? "(*)" : " (opcional)"));
		if (validateRequired && isBlank(valor)) {
			throw new IllegalArgumentException(requiredErrorMsg);
		}
		if (valor == null) return valor;
		valor = valor.trim();
		if (validateMaxLength && valor.length() > maxLength) {
			throw new IllegalArgumentException(String.format(maxLengthErrorMsg, maxLength));
		}
		return valor;
	}

	public static Banco readBancoByCodigo(final @NonNull Collection<Banco> bancos, boolean required)
	{
		final String codigo = readString("   Código del banco",
				required ? "El código del banco es requerido" : null, -1, null);

		final Predicate<Banco> withSameCodigoBanco = banco -> Objects.equals(banco.getCodigo(), codigo);
		final Optional<Banco> banco = bancos.stream()
				.filter(Objects::nonNull)
				.filter(withSameCodigoBanco)
				.findFirst();
		if ((required || isNotBlank(codigo)) && !banco.isPresent()) {
			throw new IllegalArgumentException(format("El banco con código `%s` no está disponible", codigo));
		}

		return banco.orElse(null);
	}

	public static Delincuente readDelincuenteByCodigo(final @NonNull Collection<Delincuente> delincuentes, boolean required)
	{
		final String codigo = ConsoleUI.readString("   Código del delincuente",
				required ? "El código del delincuente es requerido" : null, -1, null);

		final Predicate<Delincuente> withSameCodigoDelincuente = delincuente -> Objects.equals(delincuente.getIdentificador(), codigo);
		final Optional<Delincuente> delincuente = delincuentes.stream()
				.filter(Objects::nonNull)
				.filter(withSameCodigoDelincuente)
				.findFirst();
		if ((required || isNotBlank(codigo)) && !delincuente.isPresent()) {
			throw new IllegalArgumentException(format("El delincuente con código `%s` no está disponible", codigo));
		}

		return delincuente.orElse(null);
	}

}
