package com.example.abschlussaufgabe.data.datamodels.modelForFight



class Defense(name: String, value: Int, imagePlayer: Int, imageEnemy: Int): Attack(name, value, imagePlayer, imageEnemy) {

    init {
        isDefense = true
    }

    override fun subtractLifePoints(player: CharacterForFight,
                                    playerToChange: CharacterForFight,
                                    enemyAttack: Attack,
                                    enemyToChange: CharacterForFight,
                                    enemy: CharacterForFight,
                                    toast: (String) -> Unit) {

        if (name == "Rotation") {
            super.subtractLifePoints(player, playerToChange, enemyAttack, enemyToChange, enemy, toast)
        }
    }
}