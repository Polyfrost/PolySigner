@file:OptIn(ExperimentalCli::class)

package org.polyfrost.signing.cli

import kotlinx.cli.*
import java.nio.file.attribute.PosixFilePermission
import java.security.KeyPairGenerator
import java.security.spec.RSAKeyGenParameterSpec
import java.util.Base64
import kotlin.io.path.Path
import kotlin.io.path.setPosixFilePermissions
import kotlin.io.path.writeBytes

class GenerateSubcommand : Subcommand("generate", "Generates a signing keypair") {
    private val public by option(
        type = ArgType.String,
        shortName = "o",
        fullName = "public",
        description = "The location to output the public key to"
    )
    private val private by option(
        type = ArgType.String,
        shortName = "p",
        fullName = "private",
        description = "The location to output the private key to"
    )

    override fun execute() {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(4096)
        val keyPair = keyPairGenerator.generateKeyPair()

        val publicPath = public?.let { Path(it).toAbsolutePath() } ?: Path(System.getProperty("user.dir"), "public.key")
        val privatePath =
            private?.let { Path(it).toAbsolutePath() } ?: Path(System.getProperty("user.dir"), "private.key")
        println("Generated key, writing public to $publicPath and private to $privatePath")

        publicPath.writeBytes(keyPair.public.encoded)
        publicPath.runCatching {
            setPosixFilePermissions(
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.OTHERS_READ,
                )
            )
        }
        privatePath.writeBytes(keyPair.private.encoded)
        privatePath.runCatching {
            setPosixFilePermissions(
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE
                )
            )
        }
    }
}

fun main(args: Array<String>) {
    val parser = ArgParser("poly-signer")

    val generate = GenerateSubcommand()
    parser.subcommands(generate)

    parser.parse(args)
}