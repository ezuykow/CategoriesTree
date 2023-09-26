package ru.ezuykow.categoriestree.commands.executors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ezuykow.categoriestree.commands.CommandExecutor;
import ru.ezuykow.categoriestree.commands.ParsedCommand;
import ru.ezuykow.categoriestree.entities.Category;
import ru.ezuykow.categoriestree.messages.MessageSender;
import ru.ezuykow.categoriestree.services.CategoryService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ezuykow
 */
@Component
@RequiredArgsConstructor
public class ViewTreeCommandExec implements CommandExecutor {

    private static final String DESC = "▪";

    private final CategoryService categoryService;
    private final MessageSender messageSender;

    //-----------------API START-----------------

    /**
     * Принимает {@link ParsedCommand} и исполняет команду /viewTree
     * @param parsedCommand - команда с аргументами
     */
    @Override
    public void execute(ParsedCommand parsedCommand) {
        Set<Category> allCategories = new HashSet<>(categoryService.findAllByOwnerId(parsedCommand.ownerId()));

        String message = (allCategories.isEmpty())
                ? "В дереве нет ни одной категории"
                : createCategoryTree(allCategories, null, DESC, new StringBuilder()).toString();

        messageSender.send(parsedCommand.chatId(), message);
    }

    //-----------------API END-------------------

    private StringBuilder createCategoryTree(Set<Category> allCategories, Category parent, String shift, StringBuilder sb) {
        TreeSet<Category> categories = new TreeSet<>(Comparator.comparing(Category::getName));

        categories.addAll(allCategories.stream()
                .filter(c -> (parent == null && c.getParent() == null) || c.getParent().equals(parent))
                .collect(Collectors.toSet()));
        allCategories.removeAll(categories);

        categories.forEach(c -> {
            sb.append(shift).append(c.getName()).append("\n");
            createCategoryTree(allCategories, c, shift.concat(DESC), sb);
        });

        return sb;
    }
}
