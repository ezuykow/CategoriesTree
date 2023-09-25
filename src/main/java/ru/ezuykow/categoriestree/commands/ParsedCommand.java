package ru.ezuykow.categoriestree.commands;

import java.util.List;

/**
 * @author ezuykow
 */
public record ParsedCommand(long chatId, Command command, List<String> args) {}
