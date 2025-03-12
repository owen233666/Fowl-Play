# Fowl Play

<img src="https://cdn.modrinth.com/data/WpXfePbg/images/83e6917599cf795e9d63a22806f3c55530d029b8.png" alt="Bird showcase">

Fowl Play adds a variety of birds to Minecraft, with the aim of giving them realistic behaviours and sounds. Unlike the fluttering parrots of the base game, the avians of this mod can all take to the skies gracefully (except flightless birds, of course). Some of them add ambience, others have useful features and all of them try to annoy you as much as they can, just like real birds!

<br/>

## Important Information

This mod currently **does not have a dedicated Forge version**. If you would like to play it on Forge / NeoForged, **I have provided a Sinytra Connector compatible version**. Do keep in mind that **_Fowl Play_** being loaded with Connector can cause crashes with some mods, so this is not a guaranteed solution.

<details>
<summary>I have problems with Sinytra Connector!</summary>

**To use Sinytra connector, you must download the version labelled under Forge / NeoForged, or download older versions from my [GitHub](https://github.com/aqariio/Fowl-Play/releases)**. This is because the normal release will crash when loaded using Sinytra.

**If you are using the 1.21 version of the mod with Sinytra connector**, mobs will no longer be able to move in water. **To fix this, you will need https://github.com/unilock/sinytra1343**. ([Link to releases page](https://github.com/unilock/sinytra1343/releases))

<details>
<summary>Here is the explanation for why you need to download a special release for Sinytra connector:</summary>

The mixin redirect _fowlplay\$modifySlipperiness_ and inject _fowlplay\$increaseAirSpeed_ in LivingEntityMixin cause a crash when loaded with Sinytra connector. This is because they modify code that does not exist when loaded with Forge. Both getAirSpeed() and getSlipperiness() are implemented differently in Forge compared to Fabric, which is what causes this crash. The special jars in the GitHub releases simply remove these two mixins. As for the fix for the 1.21 version, the solution was given in [this](https://github.com/aqariio/Fowl-Play/issues/15) thread.
</details>

</details>

<details>
<summary>Wiki?</summary>

I am currently working on the wiki. It's not done yet, but you can view it **[here](https://github.com/aqariio/Fowl-Play/wiki)**
</details>

<details>
<summary>Will this mod be available on Forge?</summary>

Forge support is planned for the future, but it is not a priority at the moment, as I want to focus on adding new features and fixing bugs. If you would like to port the mod, you are completely free to do so! Just make sure to credit me and link back to this page, of course.
</details>

<details>
<summary>Can I use this mod in my modpack?</summary>

Of course! Just make sure to credit me and link back to this page.
</details>

<details>
<summary>I have a question that's not listed! / I have a suggestion!</summary>

You can talk about the mod on my [GitHub Discussions](https://github.com/aqariio/Fowl-Play/discussions)!
</details>

<br/>

## Mobs

_This mod adds a custom chicken model in the style of its other birds. The model can be disabled in the config, although a restart is required for it to take effect._

**Added mobs:**

- Penguin (Emperor penguin)
- Gull (Herring gull, ring-billed gull)
- Robin (American robin)
- Pigeon (Rock pigeon)
- Cardinal (Northern cardinal)
- Blue Jay
- Old world Sparrow (House sparrow)
- Chickadee (Black-capped chickadee)
- Raven (Common raven)
- Duck (Mallard duck)
- Hawk (Red-tailed hawk)

**Planned additions (in order of priority):**

- Eagles (Bald eagle, golden eagle)
- Old world Vultures (Griffon vulture, lappet-faced vulture)
- Ostriches (Common ostrich)
- Owls (Great horned owl, snowy owl, barn owl)
- Geese (Domestic goose, Canada goose)
- Woodpeckers (Downy woodpecker, pileated woodpecker, yellow-bellied sapsucker)
- Falcons (Peregrine falcon, gyrfalcon, American kestrel)