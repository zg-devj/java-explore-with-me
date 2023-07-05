package ru.practicum.mainservice.event;

/**
 * Изменение состояния события
 * <p>
 * SEND_TO_REVIEW - на ревью (initiator), если отменено
 * PUBLISH_EVENT - публикация (admin), если в ожидании
 * CANCEL_REVIEW - отменить (initiator), если в ожидании
 * REJECT_EVENT - отклонение (admin), если не в опубликовано
 */
public enum EventStateAction {
    SEND_TO_REVIEW(Group.Initiator), PUBLISH_EVENT(Group.Admin),
    CANCEL_REVIEW(Group.Initiator), REJECT_EVENT(Group.Admin);

    private final Group group;

    EventStateAction(Group group) {
        this.group = group;
    }

    public boolean isInGroup(Group group) {
        return this.group == group;
    }

    public enum Group {
        Admin,
        Initiator
    }
}
