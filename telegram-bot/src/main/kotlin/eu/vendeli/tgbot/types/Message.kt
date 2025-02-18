package eu.vendeli.tgbot.types

import eu.vendeli.tgbot.interfaces.MultipleResponse
import eu.vendeli.tgbot.types.chat.Chat
import eu.vendeli.tgbot.types.chat.ChatShared
import eu.vendeli.tgbot.types.forum.ForumTopicClosed
import eu.vendeli.tgbot.types.forum.ForumTopicCreated
import eu.vendeli.tgbot.types.forum.ForumTopicEdited
import eu.vendeli.tgbot.types.forum.ForumTopicReopened
import eu.vendeli.tgbot.types.forum.GeneralForumTopicHidden
import eu.vendeli.tgbot.types.forum.GeneralForumTopicUnhidden
import eu.vendeli.tgbot.types.game.Dice
import eu.vendeli.tgbot.types.game.Game
import eu.vendeli.tgbot.types.keyboard.InlineKeyboardMarkup
import eu.vendeli.tgbot.types.keyboard.WebAppData
import eu.vendeli.tgbot.types.media.Animation
import eu.vendeli.tgbot.types.media.Audio
import eu.vendeli.tgbot.types.media.Document
import eu.vendeli.tgbot.types.media.PhotoSize
import eu.vendeli.tgbot.types.media.Sticker
import eu.vendeli.tgbot.types.media.Story
import eu.vendeli.tgbot.types.media.Video
import eu.vendeli.tgbot.types.media.VideoChatEnded
import eu.vendeli.tgbot.types.media.VideoChatParticipantsInvited
import eu.vendeli.tgbot.types.media.VideoChatScheduled
import eu.vendeli.tgbot.types.media.VideoChatStarted
import eu.vendeli.tgbot.types.media.VideoNote
import eu.vendeli.tgbot.types.media.Voice
import eu.vendeli.tgbot.types.passport.PassportData
import eu.vendeli.tgbot.types.payment.Invoice
import eu.vendeli.tgbot.types.payment.SuccessfulPayment

data class Message(
    val messageId: Long,
    val messageThreadId: Long? = null,
    val from: User? = null,
    val senderChat: Chat? = null,
    val date: Int,
    val chat: Chat,
    val forwardFrom: User? = null,
    val forwardFromChat: Chat? = null,
    val forwardFromMessageId: Long? = null,
    val forwardSignature: String? = null,
    val forwardSenderName: String? = null,
    val forwardDate: Int? = null,
    val isTopicMessage: Boolean? = null,
    val isAutomaticForward: Boolean? = null,
    val replyToMessage: Message? = null,
    val viaBot: User? = null,
    val editDate: Int? = null,
    val hasProtectedContent: Boolean? = null,
    val mediaGroupId: String? = null,
    val authorSignature: String? = null,
    val text: String? = null,
    val entities: List<MessageEntity>? = null,
    val animation: Animation? = null,
    val audio: Audio? = null,
    val document: Document? = null,
    val photo: List<PhotoSize>? = null,
    val sticker: Sticker? = null,
    val story: Story? = null,
    val video: Video? = null,
    val videoNote: VideoNote? = null,
    val voice: Voice? = null,
    val caption: String? = null,
    val captionEntities: List<MessageEntity>? = null,
    val contact: Contact? = null,
    val dice: Dice? = null,
    val game: Game? = null,
    val poll: Poll? = null,
    val venue: Venue? = null,
    val location: LocationContent? = null,
    val newChatMembers: List<User>? = null,
    val leftChatMember: User? = null,
    val newChatTitle: String? = null,
    val newChatPhoto: List<PhotoSize>? = null,
    val deleteChatPhoto: Boolean? = null,
    val groupChatCreated: Boolean? = null,
    val supergroupChatCreated: Boolean? = null,
    val channelChatCreated: Boolean? = null,
    val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged? = null,
    val migrateToChatId: Long? = null,
    val migrateFromChatId: Long? = null,
    val pinnedMessage: Message? = null,
    val invoice: Invoice? = null,
    val successfulPayment: SuccessfulPayment? = null,
    val userShared: UserShared? = null,
    val chatShared: ChatShared? = null,
    val connectedWebsite: String? = null,
    val writeAccessAllowed: WriteAccessAllowed? = null,
    val passportData: PassportData? = null,
    val proximityAlertTriggered: ProximityAlertTriggered? = null,
    val forumTopicCreated: ForumTopicCreated? = null,
    val forumTopicEdited: ForumTopicEdited? = null,
    val forumTopicClosed: ForumTopicClosed? = null,
    val forumTopicReopened: ForumTopicReopened? = null,
    val generalForumTopicHidden: GeneralForumTopicHidden? = null,
    val generalForumTopicUnhidden: GeneralForumTopicUnhidden? = null,
    val videoChatScheduled: VideoChatScheduled? = null,
    val videoChatStarted: VideoChatStarted? = null,
    val videoChatEnded: VideoChatEnded? = null,
    val videoChatParticipantsInvited: VideoChatParticipantsInvited? = null,
    val webAppData: WebAppData? = null,
    val replyMarkup: InlineKeyboardMarkup? = null,
    val hasMediaSpoiler: Boolean? = null,
) : MultipleResponse
