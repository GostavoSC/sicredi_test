package gstv.sicredi.core.utils

enum class ChoicesEnum(val choice: Int) {
    BY_CATEGORY(0),
    MY_FAVOURITES(1),
    SEE_ALL(2);

    companion object {
        fun fromValue(choice: Int) = ChoicesEnum.values().firstOrNull { it.choice == choice }
    }
}