package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ai.StrategicSynthesizer
import com.example.model.StrategyVocabulary
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("StratFlow: Biz Pathway", appName)
  }

  @Test
  fun `vocabulary contains the 11 executive terms`() {
    val executiveTerms = StrategyVocabulary.EXECUTIVE_11_TERMS
    assertEquals(11, executiveTerms.size)
    val names = executiveTerms.map { it.name }
    assertTrue(names.contains("Original"))
    assertTrue(names.contains("Opposite"))
    assertTrue(names.contains("Hybrid"))
    assertTrue(names.contains("Shadow"))
    assertTrue(names.contains("Vertical"))
    assertTrue(names.contains("Horizontal"))
    assertTrue(names.contains("Adjacent"))
    assertTrue(names.contains("Integrated"))
    assertTrue(names.contains("Ecosystem"))
    assertTrue(names.contains("Flywheel"))
    assertTrue(names.contains("Platform"))
  }

  @Test
  fun `smart framework suggestion generates expected archetypes`() {
    val result = StrategicSynthesizer.suggestFramework(
      businesses = listOf("Cold Storage Hub", "Wholesale Agro Sourcing"),
      capital = "৳25 Lakh",
      context = "Bangladesh Agro Market",
      demography = "Farmers & Retailers",
      gender = "All",
      currentSelectedTerms = emptyList()
    )

    assertNotNull(result)
    assertTrue(result.recommendedFormula.contains("Original"))
    assertTrue(result.recommendedFormula.contains("Customer-first"))
    assertTrue(result.recommendedFormula.contains("Flywheel"))
    assertTrue(result.suggestedComplementaryBusinesses.isNotEmpty())
  }
}

