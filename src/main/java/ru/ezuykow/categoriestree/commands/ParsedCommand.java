package ru.ezuykow.categoriestree.commands;

import com.pengrad.telegrambot.model.Document;

import java.util.List;

/**
 * Запись с разобранной командой из апдейта, в которой хранятся: {@code chatId} - id чата, в котором отправлена команда,
 * {@code command} - объект {@link Command} с командой, список аргументов {@code args}, прикрепленный к команде документ
 * {@code document}
 * @author ezuykow
 */
public record ParsedCommand(long chatId, Command command, List<String> args, Document document, long ownerId) {}
