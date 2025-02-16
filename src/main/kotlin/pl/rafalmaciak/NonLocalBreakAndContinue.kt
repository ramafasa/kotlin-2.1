package pl.rafalmaciak

import java.time.Instant
import java.util.UUID


data class Event(
    val eventId: UUID,
    val occurredAt: Instant,
    val eventType: EventType,
)

enum class EventType {
    ORDER_CREATED,
    ORDER_CANCELLED,
    ORDER_COMPLETED,
    ORDER_PAID,
    ORDER_DELIVERED,
}

val allowedEvents = setOf(
    EventType.ORDER_CREATED,
    EventType.ORDER_CANCELLED,
    EventType.ORDER_COMPLETED,
)

object NonLocalReturn { // this worked before 2.1.0

    internal fun handleEvents(events: Set<Event>) {
        for (event in events) {

            validateEvent(event) { invalidEvent ->
                println("Invalid event $invalidEvent")
                return
            }

            println("Event $event is valid")
        }
    }

    private inline fun validateEvent(event: Event, onValidationError: (Event) -> Unit) {
        if (event.eventType !in allowedEvents) {
            onValidationError(event)
        }
    }
}

object NonLocalBreak {

    internal fun handleEvents(events: Set<Event>) {
        forEachEventsLoop@ for (event in events) {

            validateEvent(event) { invalidEvent ->
                println("Invalid event $invalidEvent")
                break@forEachEventsLoop
            }

            println("Event $event is valid")
        }
    }

    private inline fun validateEvent(event: Event, onValidationError: (Event) -> Unit) {
        if (event.eventType !in allowedEvents) {
            onValidationError(event)
        }
    }
}

object NonLocalContinue {

    internal fun handleEvents(events: Set<Event>) {
        forEachEventsLoop@ for (event in events) {

            validateEvent(event) { invalidEvent ->
                println("Invalid event $invalidEvent")
                continue@forEachEventsLoop
            }

            println("Event $event is valid")
        }
    }

    private inline fun validateEvent(event: Event, onValidationError: (Event) -> Unit) {
        if (event.eventType !in allowedEvents) {
            onValidationError(event)
        }
    }
}

fun main() {
    val events = setOf(
        Event(UUID.randomUUID(), Instant.now(), EventType.ORDER_CREATED),
        Event(UUID.randomUUID(), Instant.now(), EventType.ORDER_CANCELLED),
        Event(UUID.randomUUID(), Instant.now(), EventType.ORDER_PAID),
        Event(UUID.randomUUID(), Instant.now(), EventType.ORDER_COMPLETED),
        Event(UUID.randomUUID(), Instant.now(), EventType.ORDER_DELIVERED),
    )

//    NonLocalReturn.handleEvents(events)
//    NonLocalBreak.handleEvents(events)
    NonLocalContinue.handleEvents(events)
}

