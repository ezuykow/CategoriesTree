package ru.ezuykow.categoriestree.commands;

import com.pengrad.telegrambot.model.Document;

import java.util.List;

/**
 * @author ezuykow
 */
public record ParsedCommand(long chatId, Command command, List<String> args, Document document) {}
