package practice1;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GmailLabels {
    public static List<Label> getAllLabels(Gmail service) throws IOException {
        ListLabelsResponse response = service.users().labels().list("me").execute();
        return response.getLabels();
    }

    public static void printAllLabels(Gmail service) throws IOException {
        List<Label> labels = getAllLabels(service);
        System.out.println("Available labels:");
        for (Label label : labels) {
            System.out.printf("- %s (ID: %s, Type: %s)%n",
                    label.getName(),
                    label.getId(),
                    label.getType());
        }
    }

    public static Label createLabel(Gmail service, String labelName, String backgroundColor, String textColor)
            throws IOException {

        Label label = new Label();
        label.setName(labelName);

        if (backgroundColor != null && textColor != null) {
            LabelColor color = new LabelColor();
            color.setBackgroundColor(backgroundColor);
            color.setTextColor(textColor);
            label.setColor(color);
        }

        label.setLabelListVisibility("labelShow");
        label.setMessageListVisibility("show");

        return service.users().labels().create("me", label).execute();
    }

    public static Label createImportantLabel(Gmail service) throws IOException {
        return createLabel(service, "Важное", "#f3f3f3", "#000000");
    }

    public static Label createWorkLabel(Gmail service) throws IOException {
        return createLabel(service, "Работа", "#fef1d1", "#000000");
    }

    public static Label createPersonalLabel(Gmail service) throws IOException {
        return createLabel(service, "Личное", "#a479e0", "#ffffff");
    }

    public static Label getLabelById(Gmail service, String labelId) throws IOException {
        return service.users().labels().get("me", labelId).execute();
    }

    public static Label getLabelByName(Gmail service, String labelName) throws IOException {
        List<Label> labels = getAllLabels(service);
        for (Label label : labels) {
            if (labelName.equalsIgnoreCase(label.getName())) {
                return label;
            }
        }
        return null;
    }

    public static Label updateLabel(Gmail service, String labelId, String newName,
                             String newBackgroundColor, String newTextColor) throws IOException {

        Label label = getLabelById(service, labelId);
        if (label == null) {
            throw new IOException("Label not found: " + labelId);
        }

        if (newName != null) {
            label.setName(newName);
        }

        if (newBackgroundColor != null && newTextColor != null) {
            LabelColor color = new LabelColor();
            color.setBackgroundColor(newBackgroundColor);
            color.setTextColor(newTextColor);
            label.setColor(color);
        }

        return service.users().labels().update("me", labelId, label).execute();
    }

    public static void deleteLabel(Gmail service, String labelId) throws IOException {
        service.users().labels().delete("me", labelId).execute();
    }

    public static void deleteLabelByName(Gmail service, String labelName) throws IOException {
        Label label = getLabelByName(service, labelName);
        if (label != null) {
            deleteLabel(service, label.getId());
        }
    }

    public static Message addLabelsToMessage(Gmail service, String messageId, List<String> labelIds) throws IOException {
        ModifyMessageRequest mods = new ModifyMessageRequest();
        mods.setAddLabelIds(labelIds);

        return service.users().messages().modify("me", messageId, mods).execute();
    }

    public static Message addLabelToMessageByName(Gmail service, String messageId, String labelName) throws IOException {
        Label label = getLabelByName(service, labelName);
        if (label == null) {
            throw new IOException("Метка не найдена: " + labelName);
        }

        List<String> labelIds = new ArrayList<>();
        labelIds.add(label.getId());

        return addLabelsToMessage(service, messageId, labelIds);
    }

    public static Message removeLabelsFromMessage(Gmail service, String messageId, List<String> labelIds) throws IOException {
        ModifyMessageRequest mods = new ModifyMessageRequest();
        mods.setRemoveLabelIds(labelIds);

        return service.users().messages().modify("me", messageId, mods).execute();
    }

    public static Message removeAllCustomLabels(Gmail service, String messageId) throws IOException {
        Message message = service.users().messages().get("me", messageId).execute();
        List<String> labelIds = message.getLabelIds();

        List<String> customLabelIds = new ArrayList<>();
        for (String labelId : labelIds) {
            if (!labelId.startsWith("CATEGORY_") && !labelId.equals("INBOX") &&
                    !labelId.equals("SENT") && !labelId.equals("DRAFT") && !labelId.equals("UNREAD")) {
                customLabelIds.add(labelId);
            }
        }

        return removeLabelsFromMessage(service, messageId, customLabelIds);
    }

    public List<Label> getMessageLabels(Gmail service, String messageId) throws IOException {
        Message message = service.users().messages().get("me", messageId).execute();
        List<String> labelIds = message.getLabelIds();

        List<Label> labels = new ArrayList<>();
        for (String labelId : labelIds) {
            try {
                Label label = getLabelById(service, labelId);
                if (label != null) {
                    labels.add(label);
                }
            } catch (IOException ignored) {
            }
        }

        return labels;
    }
}
